/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file AxisCamera.cpp
 \brief File patching buggy code for the AxisCamera class that causes the robot to freeze.
 \authors FIRST people, Joe Hurler
 */
/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.							  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in $(WIND_BASE)/WPILib.  */
/*----------------------------------------------------------------------------*/

#include <string.h>
#include "Synchronized.h"
#include "Vision/AxisCamera.h"
#include "Vision/PCVideoServer.h"

/* Private NI function to decode JPEG */
IMAQ_FUNC int Priv_ReadJPEGString_C(Image* _image, const unsigned char* _string, UINT32 _stringLength);

// Max packet without jumbo frames is 1500... add 36 because??
#define kMaxPacketSize 1536
#define kImageBufferAllocationIncrement 1000

AxisCamera* AxisCamera::m_instance = NULL;

/*
 \brief AxisCamera constructor
 */
AxisCamera::AxisCamera(const char *ipAddress)
	: AxisCameraParams(ipAddress)
	, m_cameraSocket (0)
	, m_protectedImageBuffer(NULL)
	, m_protectedImageBufferLength (0)
	, m_protectedImageSize (0)
	, m_protectedImageSem (NULL)
	, m_freshImage (false)
	, m_imageStreamTask("cameraTask", (FUNCPTR)s_ImageStreamTaskFunction)
{
	m_protectedImageSem = semMCreate(SEM_Q_PRIORITY | SEM_INVERSION_SAFE | SEM_DELETE_SAFE);

	m_imageStreamTask.Start((int)this);
}

/*
 \brief Destructor
 */
AxisCamera::~AxisCamera()
{
	m_imageStreamTask.Stop();
	close(m_cameraSocket);

	SemSet_t::iterator it = m_newImageSemSet.begin();
	SemSet_t::iterator end = m_newImageSemSet.end();
	for (;it != end; it++)
	{
		semDelete(*it);
	}
	m_newImageSemSet.clear();

	semDelete(m_protectedImageSem);
	m_instance = NULL;
}

/*
 \brief Get a pointer to the AxisCamera object, if the object does not exist, create it
 \return reference to AxisCamera object
 */
AxisCamera& AxisCamera::GetInstance()
{
	if (NULL == m_instance) {
		// Since this is a singleton for now, just use the default IP address.
		m_instance = new AxisCamera();
		// TODO: Keep track of this so it can be shut down!
		new PCVideoServer();
	}
	return *m_instance;
}

/*
 \brief Called by Java to delete the camera... how thoughtful
 */
void AxisCamera::DeleteInstance()
{
	delete m_instance;
}

/*
 \brief Return true if the latest image from the camera has not been retrieved by calling GetImage() yet.
 \return true if the image has not been retrieved yet.
 */
bool AxisCamera::IsFreshImage()
{
	return m_freshImage;
}

/*
 \brief Get the semaphore to be used to synchronize image access with camera acquisition
 \details Call semTake on the returned semaphore to block until a new image is acquired. The semaphore is owned by the AxisCamera class and will be deleted when the class is destroyed.
 \return A semaphore to notify when new image is received
 */
SEM_ID AxisCamera::GetNewImageSem()
{
	SEM_ID sem = semBCreate (SEM_Q_PRIORITY, SEM_EMPTY);
	m_newImageSemSet.insert(sem);
	return sem;
}

/*
 \brief Get an image from the camera and store it in the provided image.
 \param image The imaq image to store the result in. This must be an HSL or RGB image
 \details This function is called by Java.
 \return 1 upon success, zero on a failure
 */
int AxisCamera::GetImage(Image* imaqImage)
{
	if (m_protectedImageBuffer == NULL)
		return 0;
	Synchronized sync(m_protectedImageSem);
	Priv_ReadJPEGString_C(imaqImage,
		(unsigned char*)m_protectedImageBuffer, m_protectedImageSize);
	m_freshImage = false;
	return 1;
}

/*
 \brief Get an image from the camera and store it in the provided image.
 \param image The image to store the result in. This must be an HSL or RGB image
 \return 1 upon success, zero on a failure
 */
int AxisCamera::GetImage(ColorImage* image)
{
	return GetImage(image->GetImaqImage());
}

/*
 \brief Instantiate a new image object and fill it with the latest image from the camera.
 \details The returned pointer is owned by the caller and is their responsibility to delete.
 \return a pointer to an HSLImage object
 */
HSLImage* AxisCamera::GetImage()
{
	HSLImage *image = new HSLImage();
	GetImage(image);
	return image;
}

/*
 \brief Copy an image into an existing buffer.
 \details This copies an image into an existing buffer rather than creating a new image in memory. That way a new image is only allocated when the image being copied is larger than the destination. This method is called by the PCVideoServer class.
 \param imageData The destination image.
 \param numBytes The size of the destination image.
 \return 0 if failed (no source image or no memory), 1 if success.
 */
int AxisCamera::CopyJPEG(char **destImage, int &destImageSize, int &destImageBufferSize)
{
	Synchronized sync(m_protectedImageSem);
	wpi_assert(destImage != NULL);
	if (m_protectedImageBuffer == NULL) return 0; // if no source image
	if (destImageBufferSize < m_protectedImageSize) // if current destination buffer too small
	{
		if (*destImage != NULL) delete [] *destImage;
		destImageBufferSize = m_protectedImageSize + kImageBufferAllocationIncrement;
		*destImage = new char[destImageBufferSize];
		if (*destImage == NULL) return 0;
	}
	// copy this image into destination buffer
	wpi_assert(*destImage != NULL);
	wpi_assert(m_protectedImageBuffer != NULL);
	wpi_assert(m_protectedImageSize > 0);
	// TODO: Is this copy realy necessary... perhaps we can simply transmit while holding the protected buffer
	memcpy(*destImage, m_protectedImageBuffer, m_protectedImageSize);
	destImageSize = m_protectedImageSize;
	return 1;
}

/*
 \brief Static interface that will cause an instantiation if necessary.
 \details This static stub is directly spawned as a task to read images from the camera.
 */
int AxisCamera::s_ImageStreamTaskFunction(AxisCamera *thisPtr)
{
	return thisPtr->ImageStreamTaskFunction();
}

/*
 \brief Task spawned by AxisCamera constructor to receive images from cam
 \details If setNewImageSem has been called, this function does a semGive on each new image.  Images can be accessed by calling getImage().
 */
int AxisCamera::ImageStreamTaskFunction()
{
	// Loop on trying to setup the camera connection. This happens in a background
	// thread so it shouldn't effect the operation of user programs.
	while (1)
	{
		char * requestString = "GET /mjpg/video.mjpg HTTP/1.1\n\
User-Agent: HTTPStreamClient\n\
Connection: Keep-Alive\n\
Cache-Control: no-cache\n\
Authorization: Basic RlJDOkZSQw==\n\n";
		semTake(m_socketPossessionSem, WAIT_FOREVER);
		m_cameraSocket = CreateCameraSocket(requestString);
		if (m_cameraSocket == 0)
		{
			// Don't hammer the camera if it isn't ready.
			semGive(m_socketPossessionSem);
			taskDelay(1000);
		}
		else
		{
			ReadImagesFromCamera();
		}
	}
}

/*
 \brief This function actually reads the images from the camera.
 */
int AxisCamera::ReadImagesFromCamera()
{
	char *imgBuffer = NULL;
	int imgBufferLength = 0;
	//Infinite loop, task deletion handled by taskDeleteHook
	// Socket cleanup handled by destructor

	// TODO: these recv calls must be non-blocking. Otherwise if the camera
	// fails during a read, the code hangs and never retries when the camera comes
	// back up.

	int counter = 2;
	while (1)
	{
		char initialReadBuffer[kMaxPacketSize] = "";
		char intermediateBuffer[1];
		char *trailingPtr = initialReadBuffer;
		int trailingCounter = 0;
		while (counter)
		{
			// TODO: fix me... this cannot be the most efficient way to approach this, reading one byte at a time.
			if(recv(m_cameraSocket, intermediateBuffer, 1, 0) == ERROR)
			{
				perror ("AxisCamera: Failed to read image header");
				close (m_cameraSocket);
				return (ERROR);
			}
			strncat(initialReadBuffer, intermediateBuffer, 1);
			// trailingCounter ensures that we start looking for the 4 byte string after
			// there is at least 4 bytes total. Kind of obscure.
			// look for 2 blank lines (\r\n)
			if (NULL != strstr(trailingPtr, "\r\n\r\n"))
			{
				--counter;
			}
			if (++trailingCounter >= 4)
			{
				trailingPtr++;
			}
		}
		counter = 1;
		char *contentLength = strstr(initialReadBuffer, "Content-Length: ");
		if (contentLength == NULL)
		{
			perror("AxisCamera: No content-length token found in packet");
			close(m_cameraSocket);
			return(ERROR);
		}
		contentLength = contentLength + 16; // skip past "content length"
		int readLength = atol(contentLength); // get the image byte count

		// Make sure buffer is large enough
		if (imgBufferLength < readLength)
		{
			if (imgBuffer) delete[] imgBuffer;
			imgBufferLength = readLength + kImageBufferAllocationIncrement;
			imgBuffer = new char[imgBufferLength];
			if (imgBuffer == NULL)
			{
				imgBufferLength = 0;
				continue;
			}
		}

		// Read the image data for "Content-Length" bytes
		int bytesRead = 0;
		int remaining = readLength;
		while(bytesRead < readLength)
		{
			int bytesThisRecv = recv(m_cameraSocket, &imgBuffer[bytesRead], remaining, 0);
			bytesRead += bytesThisRecv;
			remaining -= bytesThisRecv;
		}
		// Update image
		UpdatePublicImageFromCamera(imgBuffer, readLength);
		if (semTake(m_paramChangedSem, NO_WAIT) == OK)
		{
			// params need to be updated: close the video stream; release the camera.
			close(m_cameraSocket);
			semGive(m_socketPossessionSem);
			return(0);
		}
	}
}

/*
 \brief Copy the image from private buffer to shared buffer.
 \param imgBuffer The buffer containing the image
 \param bufLength The length of the image
 */
void AxisCamera::UpdatePublicImageFromCamera(char *imgBuffer, int imgSize)
{
	{
		Synchronized sync(m_protectedImageSem);

		// Adjust the buffer size if current destination buffer is too small.
		if (m_protectedImageBufferLength < imgSize)
		{
			if (m_protectedImageBuffer != NULL) delete [] m_protectedImageBuffer;
			m_protectedImageBufferLength = imgSize + kImageBufferAllocationIncrement;
			m_protectedImageBuffer = new char[m_protectedImageBufferLength];
			if (m_protectedImageBuffer == NULL)
			{
				m_protectedImageBufferLength = 0;
				return;
			}
		}

		memcpy(m_protectedImageBuffer, imgBuffer, imgSize);
		m_protectedImageSize = imgSize;
	}

	m_freshImage = true;
	// Notify everyone who is interested.
	SemSet_t::iterator it = m_newImageSemSet.begin();
	SemSet_t::iterator end = m_newImageSemSet.end();
	for (;it != end; it++)
	{
		semGive(*it);
	}
}

/*
 \brief Implement the pure virtual interface so that when parameter changes require a restart, the image task can be bounced.
 */
void AxisCamera::RestartCameraTask()
{
	m_imageStreamTask.Stop();
	m_imageStreamTask.Start((int)this);
}


// C bindings used by Java
// These need to stay as is or Java has to change

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraStart()
{
	AxisCamera::GetInstance();
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetImage (Image* image)
{
	return AxisCamera::GetInstance().GetImage(image);
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteBrightness(int brightness)
{
	AxisCamera::GetInstance().WriteBrightness(brightness);
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetBrightness()
{
	return AxisCamera::GetInstance().GetBrightness();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteWhiteBalance(AxisCameraParams::WhiteBalance_t whiteBalance)
{
	AxisCamera::GetInstance().WriteWhiteBalance(whiteBalance);
}

// C binding used by Java; must stay as is or Java has to change.
AxisCameraParams::WhiteBalance_t AxisCameraGetWhiteBalance()
{
	return AxisCamera::GetInstance().GetWhiteBalance();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteColorLevel(int colorLevel)
{
	AxisCamera::GetInstance().WriteColorLevel(colorLevel);
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetColorLevel()
{
	return AxisCamera::GetInstance().GetColorLevel();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteExposureControl(AxisCameraParams::Exposure_t exposure)
{
	AxisCamera::GetInstance().WriteExposureControl(exposure);
}

// C binding used by Java; must stay as is or Java has to change.
AxisCameraParams::Exposure_t AxisCameraGetExposureControl()
{
	return AxisCamera::GetInstance().GetExposureControl();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteExposurePriority(int exposure)
{
	AxisCamera::GetInstance().WriteExposurePriority(exposure);
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetExposurePriority()
{
	return AxisCamera::GetInstance().GetExposurePriority();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteMaxFPS(int maxFPS)
{
	AxisCamera::GetInstance().WriteMaxFPS(maxFPS);
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetMaxFPS()
{
	return AxisCamera::GetInstance().GetMaxFPS();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteResolution(AxisCameraParams::Resolution_t resolution)
{
	AxisCamera::GetInstance().WriteResolution(resolution);
}

// C binding used by Java; must stay as is or Java has to change.
AxisCameraParams::Resolution_t AxisCameraGetResolution()
{
	return AxisCamera::GetInstance().GetResolution();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteCompression(int compression)
{
	AxisCamera::GetInstance().WriteCompression(compression);
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraGetCompression()
{
	return AxisCamera::GetInstance().GetCompression();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraWriteRotation(AxisCameraParams::Rotation_t rotation)
{
	AxisCamera::GetInstance().WriteRotation(rotation);
}

// C binding used by Java; must stay as is or Java has to change.
AxisCameraParams::Rotation_t AxisCameraGetRotation()
{
	return AxisCamera::GetInstance().GetRotation();
}

// C binding used by Java; must stay as is or Java has to change.
void AxisCameraDeleteInstance()
{
	AxisCamera::GetInstance().DeleteInstance();
}

// C binding used by Java; must stay as is or Java has to change.
int AxisCameraFreshImage()
{
	return AxisCamera::GetInstance().IsFreshImage();
}
