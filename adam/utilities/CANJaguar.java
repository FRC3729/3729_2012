
package utilities;

import utilities.*;
import edu.wpi.first.wpilibj.SpeedController;

public class CANJaguar implements JaguarCANProtocol, SpeedController
{
    /**
     * Mode determines how the Jaguar is controlled
     */
    public static class ControlMode
    {
        public final int value;
        static final int kPercentVoltage_val = 0;
        static final int kSpeed_val = 1;
        static final int kPosition_val = 2;
        static final int kCurrent_val = 3;

        public static final ControlMode kPercentVoltage = new ControlMode(kPercentVoltage_val);
        public static final ControlMode kSpeed = new ControlMode(kSpeed_val);
        public static final ControlMode kPosition = new ControlMode(kPosition_val);
        public static final ControlMode kCurrent = new ControlMode(kCurrent_val);

        private ControlMode(int value) {
            this.value = value;
        }
    }

    /**
     * Faults reported by the Jaguar
     */
    public static class Faults
    {
        public final int value;
        static final int kCurrentFault_val = 1;
        static final int kTemperatureFault_val = 2;
        static final int kBusVoltageFault_val = 4;

        public static final Faults kCurrentFault = new Faults(kCurrentFault_val);
        public static final Faults kTemperatureFault = new Faults(kTemperatureFault_val);
        public static final Faults kBusVoltageFault = new Faults(kBusVoltageFault_val);

        private Faults(int value) {
            this.value = value;
        }
    }

    /**
     * Limit switch masks
     */
    public static class Limits
    {
        public final int value;
        static final int kForwardLimit_val = 1;
        static final int kReverseLimit_val = 2;

        public static final Limits kForwardLimit = new Limits(kForwardLimit_val);
        public static final Limits kReverseLimit = new Limits(kReverseLimit_val);

        private Limits(int value) {
            this.value = value;
        }
    }

    private char m_deviceNumber;
    private ControlMode m_controlMode;
    private static final byte[] kNoData = new byte[0];

    private final static int swap16(int x)
    {
        return ( (((x)>>>8) & 0x00FF )
            | (((x)<<8) & 0xFF00) );
    }
    private final static long swap32(long x)
    {
        return ( (((x)>>24)& 0x000000FF)
            | (((x)>>8) & 0x0000FF00)
            | (((x)<<8) & 0x00FF0000)
            | (((x)<<24)& 0xFF000000) );
    }

    /**
     * Pack 16-bit data in little-endian byte order
     * @param data The data to be packed
     * @param buffer The buffer to pack into
     * @param offset The offset into data to pack the variable
     */
    private static final void pack16(short data, byte[] buffer, int offset)
    {
        buffer[offset] = (byte)(data & 0xFF);
        buffer[offset+1] = (byte)((data >>> 8) & 0xFF);
    }

    /**
     * Pack 32-bit data in little-endian byte order
     * @param data The data to be packed
     * @param buffer The buffer to pack into
     * @param offset The offset into data to pack the variable
     */
    private static final void pack32(int data, byte[] buffer, int offset)
    {
        buffer[offset] = (byte)(data & 0xFF);
        buffer[offset+1] = (byte)((data >>> 8) & 0xFF);
        buffer[offset+2] = (byte)((data >>> 16) & 0xFF);
        buffer[offset+3] = (byte)((data >>> 24) & 0xFF);
    }

    /**
     * Unpack 16-bit data from a buffer in little-endian byte order
     * @param buffer The buffer to unpack from
     * @param offset The offset into he buffer to unpack
     * @return The data that was unpacked
     */
    private static final short unpack16(byte[] buffer, int offset)
    {
        return (short)(((int)buffer[offset] & 0xFF) | (short)((buffer[offset+1] << 8))& 0xFF00);
    }

    /**
     * Unpack 32-bit data from a buffer in little-endian byte order
     * @param buffer The buffer to unpack from
     * @param offset The offset into he buffer to unpack
     * @return The data that was unpacked
     */
    private static final int unpack32(byte[] buffer, int offset)
    {
        return ((int)buffer[offset] & 0xFF) | ((buffer[offset+1] << 8) & 0xFF00) |
            ((buffer[offset+2] << 16) & 0xFF0000) | ((buffer[offset+3] << 24) & 0xFF000000);
    }

    private static final int kFullMessageIDMask = 0x1FFFFFC0;

    /**
     * Common initialization code called by all constructors.
     */
    private void initJaguar ()
    {
        if (m_deviceNumber < 1 || m_deviceNumber > 63)
        {
            // TODO: Error
        }
        if (m_controlMode == ControlMode.kPercentVoltage)
        {
            sendMessage(LM_API_VOLT_T_EN | m_deviceNumber, kNoData, 0);
        }
        else
        {
            // TODO: Not implemented yet
        }
    }

    /**
     * Constructor
     * Default to voltage control mode.
     * @param deviceNumber The the address of the Jaguar on the CAN bus.
     */
    public CANJaguar(int deviceNumber)
    {
	m_deviceNumber = (char)deviceNumber;
	m_controlMode = ControlMode.kPercentVoltage;
        initJaguar();
    }

    /**
     * Constructor
     * @param deviceNumber The the address of the Jaguar on the CAN bus.
     * @param controlMode The control mode that the Jaguar will run in.
     */
    public CANJaguar(int deviceNumber, ControlMode controlMode)
    {
	m_deviceNumber = (char)deviceNumber;
	m_controlMode = controlMode;
        initJaguar();
    }

    /**
     * Set the output set-point value.
     *
     * In PercentVoltage Mode, the input is in the range -1.0 to 1.0
     *
     * @param outputValue The set-point to sent to the motor controller.
     */
    public void set(double outputValue)
    {
        int messageID;
        byte[] dataBuffer = new byte[2];
        byte dataSize;

        if (m_controlMode == ControlMode.kPercentVoltage)
        {
            messageID = LM_API_VOLT_T_SET | m_deviceNumber;
            short value = (short)(outputValue * 32767.0);
            //System.out.println(value);
            pack16(value, dataBuffer, 0);
            dataSize = 2;
        }
        else
        {
            // TODO: Not implemented yet
            return;
        }
        sendMessage(messageID, dataBuffer, dataSize);
    }

    /**
     * Get the recently set outputValue setpoint.
     *
     * In PercentVoltage Mode, the outputValue is in the range -1.0 to 1.0
     *
     * @return The most recently set outputValue setpoint.
     */
    public double get()
    {
        int messageID;
        byte[] dataBuffer;
        byte dataSize;

        if (m_controlMode == ControlMode.kPercentVoltage)
        {
            messageID = LM_API_VOLT_SET | m_deviceNumber;
            // Sending set with no data is a request for the last set
            sendMessage(messageID, kNoData, 0);
            dataBuffer = new byte[2];
            dataSize = receiveMessage(messageID, dataBuffer);
            if (dataSize == 2)
            {
                short replyValue = unpack16(dataBuffer, 0);
                return replyValue / 32767.0;
            }
            else
            {
                // TODO: Error
                return 0.0;
            }
        }
        else
        {
            // TODO: Not implemented yet
            return 0.0;
        }
    }

    /**
     * Write out the PID value as seen in the PIDOutput base object.
     *
     * @param output Write out the percentage voltage value as was computed by the PIDController
     */
    public void pidWrite(double output)
    {
	if (m_controlMode == ControlMode.kPercentVoltage)
	{
            set(output);
	}
	else
	{
            // TODO: Error... only voltage mode supported for PID API
	}
    }

    private static final byte[] sendTrustedDataBuffer = new byte[JaguarCANDriver.kMaxMessageDataSize];
    /**
     * Send a message on the CAN bus through the CAN driver in FRC_NetworkCommunication
     *
     * Trusted messages require a 2-byte token at the beginning of the data payload.
     * If the message being sent is trusted, make space for the token.
     *
     * @param messageID The messageID to be used on the CAN bus
     * @param data The up to 8 bytes of data to be sent with the message
     * @param dataSize Specify how much of the data in "data" to send
     */
    protected void sendMessage(int messageID, byte[] data, int dataSize)
    {
	final int[] kTrustedMessages = {
			LM_API_VOLT_T_EN, LM_API_VOLT_T_SET, LM_API_SPD_T_EN, LM_API_SPD_T_SET,
			LM_API_POS_T_EN, LM_API_POS_T_SET, LM_API_ICTRL_T_EN, LM_API_ICTRL_T_SET};

        byte i;
	for (i=0; i<kTrustedMessages.length; i++)
	{
            if ((kFullMessageIDMask & messageID) == kTrustedMessages[i])
            {
                sendTrustedDataBuffer[0] = 0;
                sendTrustedDataBuffer[1] = 0;
                // Make sure the data will still fit after adjusting for the token.
                if (dataSize > JaguarCANDriver.kMaxMessageDataSize - 2)
                {
                    // TODO: Error
                    return;
                }
                byte j;
                for (j=0; j < dataSize; j++)
                {
                    sendTrustedDataBuffer[j + 2] = data[j];
                }
                JaguarCANDriver.sendMessage(messageID, sendTrustedDataBuffer, dataSize + 2);
                return;
            }
	}
	JaguarCANDriver.sendMessage(messageID, data, dataSize);
    }

    /**
     * Receive a message from the CAN bus through the CAN driver in FRC_NetworkCommunication
     *
     * @param messageID The messageID to read from the CAN bus
     * @param data The up to 8 bytes of data that was received with the message
     * @param dataSize Indicates how much data was received
     * @param timeout Specify how long to wait for a message (in seconds)
     */
    protected byte receiveMessage(int messageID, byte[] data, double timeout)
    {
        JaguarCANDriver canDriver = new JaguarCANDriver();
	byte dataSize = canDriver.receiveMessage(messageID, data, timeout);
        return dataSize;
    }
    protected byte receiveMessage(int messageID, byte[] data)
    {
        return receiveMessage(messageID, data, 0.1);
    }

    /**
     * Get the voltage at the battery input terminals of the Jaguar.
     *
     * @return The bus voltage in Volts.
     */
    public double getBusVoltage()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_VOLTBUS | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 2)
	{
            return unpack16(dataBuffer, 0) / 256.0;
	}
	return 0.0;
    }

    /**
     * Get the voltage being output from the motor terminals of the Jaguar.
     *
     * @return The output voltage in Volts.
     */
    public double getOutputVoltage()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;
	double busVoltage = getBusVoltage();

        // Then read the volt out which is in percentage of bus voltage units.
        messageID = LM_API_STATUS_VOLTOUT | m_deviceNumber;
        // Sending set with no data is a request for the last set
        sendMessage(messageID, kNoData, 0);
        dataSize = receiveMessage(messageID, dataBuffer);
        if (dataSize == 2)
        {
            return busVoltage * unpack16(dataBuffer, 0) / 32767.0;
        }
	return 0.0;
    }

    /**
     * Get the current through the motor terminals of the Jaguar.
     *
     * @return The output current in Amps.
     */
    public double getOutputCurrent()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_CURRENT | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 2)
	{
		return unpack16(dataBuffer, 0) / 256.0;
	}
	return 0.0;
    }

    /**
     * Get the internal temperature of the Jaguar.
     *
     * @return The temperature of the Jaguar in degrees Celsius.
     */
    public double getTemperature()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_TEMP | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 2)
	{
            return unpack16(dataBuffer, 0) / 256.0;
	}
	return 0.0;
    }

    /**
     * Get the position of the encoder or potentiometer.
     *
     * @return The position of the motor based on the configured feedback.
     */
    public int getPosition()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_POS | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 4)
	{
            return unpack32(dataBuffer, 0);
	}
	return 0;
    }

    /**
     * Get the speed of the encoder.
     *
     * @return The speed of the motor in RPM based on the configured feedback.
     */
    public double getSpeed()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_SPD | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 4)
	{
            return unpack32(dataBuffer, 0) / 65536.0;
	}
	return 0.0;
    }

    /**
     * Get the status of the forward limit switch.
     *
     * @return The motor is allowed to turn in the forward direction when true.
     */
    public boolean getForwardLimitOK()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_LIMIT | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 1)
	{
            return (dataBuffer[0] & Limits.kForwardLimit.value) != 0;
	}
	return false;
    }

    /**
     * Get the status of the reverse limit switch.
     *
     * @return The motor is allowed to turn in the reverse direction when true.
     */
    public boolean getReverseLimitOK()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_LIMIT | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 1)
	{
            return (dataBuffer[0] & Limits.kReverseLimit.value) != 0;
	}
	return false;
    }

    /**
     * Get the status of any faults the Jaguar has detected.
     *
     * @return The motor is allowed to turn in the reverse direction when true.
     */
    public short getFaults()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_FAULT | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 2)
	{
            return unpack16(dataBuffer, 0);
	}
	return 0;
    }

    /**
     * Check if the Jaguar's power has been cycled since this was last called.
     *
     * This should return true the first time called after a Jaguar power up,
     * and false after that.
     *
     * @return The Jaguar was power cycled since the last call to this function.
     */
    public boolean getPowerCycled()
    {
	int messageID;
	byte[] dataBuffer = new byte[8];
	byte dataSize;

	messageID = LM_API_STATUS_POWER | m_deviceNumber;
	// Sending set with no data is a request for the last set
	sendMessage(messageID, kNoData, 0);
	dataSize = receiveMessage(messageID, dataBuffer);
	if (dataSize == 1)
	{
            boolean powerCycled = dataBuffer[0] != 0;

            // Clear the power cycled bit now that we've accessed it
            dataBuffer = new byte[1];
            dataBuffer[0] = 1;
            sendMessage(messageID, dataBuffer, 1);

            return powerCycled;
	}
	return false;
    }


}
