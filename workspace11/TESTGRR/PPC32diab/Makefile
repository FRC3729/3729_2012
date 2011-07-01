# Wind River Workbench generated Makefile.
# Do not edit!!!
#
# The file ".wrmakefile" is the template used by the Wind River Workbench to
# generate the makefiles of this project. Add user-specific build targets and
# make rules only(!) in this project's ".wrmakefile" file. These will then be
# automatically dumped into the makefiles.

WIND_HOME := $(subst \,/,$(WIND_HOME))
WIND_BASE := $(subst \,/,$(WIND_BASE))
WIND_USR := $(subst \,/,$(WIND_USR))

all : pre_build main_all post_build

_clean ::
	@echo "make: removing targets and objects of `pwd`"

TRACE=0
TRACEON=$(TRACE:0=@)
TRACE_FLAG=$(TRACEON:1=)

MAKEFILE := Makefile

FLEXIBLE_BUILD := 1

BUILD_SPEC = PPC32diab
DEBUG_MODE = 1
ifeq ($(DEBUG_MODE),1)
MODE_DIR := Debug
else
MODE_DIR := NonDebug
endif
OBJ_DIR := .
PRJ_ROOT_DIR := C:/WindRiver/frc/frc/workspace/TESTGRR
WS_ROOT_DIR := C:/WindRiver/frc/frc/workspace



#Global Build Macros
PROJECT_TYPE = DKM
DEFINES = 
EXPAND_DBG = 0


#BuildSpec specific Build Macros
VX_CPU_FAMILY = ppc
CPU = PPC32
TOOL_FAMILY = diab
TOOL = diab
TOOL_PATH = 
CC_ARCH_SPEC = -tPPCFH:vxworks63 -Xstmw-slow -DPPC32_fp60x
LIBPATH = 
LIBS = 

IDE_INCLUDES = -I$(WIND_BASE)/target/h -I$(WIND_BASE)/target/h/wrn/coreip 

IDE_LIBRARIES = 



#BuildTool flags
ifeq ($(DEBUG_MODE),1)
DEBUGFLAGS_C-Compiler = -g
DEBUGFLAGS_C++-Compiler = -g
DEBUGFLAGS_Linker = -g
DEBUGFLAGS_Partial-Image-Linker = 
DEBUGFLAGS_Librarian = 
DEBUGFLAGS_Assembler = -g
else
DEBUGFLAGS_C-Compiler = -XO -Xsize-opt
DEBUGFLAGS_C++-Compiler = -XO -Xsize-opt
DEBUGFLAGS_Linker = -XO -Xsize-opt
DEBUGFLAGS_Partial-Image-Linker = 
DEBUGFLAGS_Librarian = 
DEBUGFLAGS_Assembler = -XO -Xsize-opt
endif


#Project Targets
PROJECT_TARGETS = TESTGRR/$(MODE_DIR)/TESTGRR.out \
	 TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage.o


#Rules

# TESTGRR
ifeq ($(DEBUG_MODE),1)
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_C-Compiler = -g
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_C++-Compiler = -g
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Linker = -g
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Partial-Image-Linker = 
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Librarian = 
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Assembler = -g
else
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_C-Compiler = -XO -Xsize-opt
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_C++-Compiler = -XO -Xsize-opt
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Linker = -XO -Xsize-opt
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Partial-Image-Linker = 
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Librarian = 
TESTGRR/$(MODE_DIR)/% : DEBUGFLAGS_Assembler = -XO -Xsize-opt
endif
TESTGRR/$(MODE_DIR)/% : IDE_INCLUDES = -I$(WIND_BASE)/target/h -I$(WIND_BASE)/target/h/wrn/coreip 
TESTGRR/$(MODE_DIR)/% : IDE_LIBRARIES = 
TESTGRR/$(MODE_DIR)/% : PROJECT_TYPE = DKM
TESTGRR/$(MODE_DIR)/% : DEFINES = 
TESTGRR/$(MODE_DIR)/% : EXPAND_DBG = 0
TESTGRR/$(MODE_DIR)/% : VX_CPU_FAMILY = ppc
TESTGRR/$(MODE_DIR)/% : CPU = PPC32
TESTGRR/$(MODE_DIR)/% : TOOL_FAMILY = diab
TESTGRR/$(MODE_DIR)/% : TOOL = diab
TESTGRR/$(MODE_DIR)/% : TOOL_PATH = 
TESTGRR/$(MODE_DIR)/% : CC_ARCH_SPEC = -tPPCFH:vxworks63 -Xstmw-slow -DPPC32_fp60x
TESTGRR/$(MODE_DIR)/% : LIBPATH = 
TESTGRR/$(MODE_DIR)/% : LIBS = 
TESTGRR/$(MODE_DIR)/% : OBJ_DIR := TESTGRR/$(MODE_DIR)

OBJECTS_TESTGRR = TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage.o

TESTGRR/$(MODE_DIR)/TESTGRR.out : $(OBJECTS_TESTGRR)
	$(TRACE_FLAG)if [ ! -d "`dirname "$@"`" ]; then mkdir -p "`dirname "$@"`"; fi;echo "building $@";rm -f "$@";ddump -Ng $(OBJECTS_TESTGRR) $(IDE_LIBRARIES) | tclsh $(WIND_BASE)/host/resource/hutils/tcl/munch.tcl -c ppc > $(OBJ_DIR)/ctdt.c; $(TOOL_PATH)dcc $(DEBUGFLAGS_Linker) $(CC_ARCH_SPEC) -Xdollar-in-ident -Xforce-declarations $(ADDED_CFLAGS) $(IDE_INCLUDES) $(ADDED_INCLUDES) -DCPU=$(CPU) -DTOOL_FAMILY=$(TOOL_FAMILY) -DTOOL=$(TOOL) -D_WRS_KERNEL   $(DEFINES)  -o $(OBJ_DIR)/ctdt.o -c $(OBJ_DIR)/ctdt.c; $(TOOL_PATH)dld -tPPCFH:vxworks63 -X -r5 -r4 -o "$@" $(OBJ_DIR)/ctdt.o $(OBJECTS_TESTGRR) $(IDE_LIBRARIES) $(LIBPATH) $(LIBS)  $(ADDED_LIBPATH) $(ADDED_LIBS) && if [ "$(EXPAND_DBG)" = "1" ]; then plink "$@";fi

TESTGRR/$(MODE_DIR)/TESTGRR_compile_file : $(FILE) ;

_clean :: TESTGRR/$(MODE_DIR)/TESTGRR_clean

TESTGRR/$(MODE_DIR)/TESTGRR_clean : 
	$(TRACE_FLAG)if [ -d "TESTGRR" ]; then cd "TESTGRR"; rm -rf $(MODE_DIR); fi


# TESTGRR_partialImage
ifeq ($(DEBUG_MODE),1)
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_C-Compiler = -g
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_C++-Compiler = -g
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Linker = -g
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Partial-Image-Linker = 
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Librarian = 
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Assembler = -g
else
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_C-Compiler = -XO -Xsize-opt
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_C++-Compiler = -XO -Xsize-opt
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Linker = -XO -Xsize-opt
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Partial-Image-Linker = 
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Librarian = 
TESTGRR_partialImage/$(MODE_DIR)/% : DEBUGFLAGS_Assembler = -XO -Xsize-opt
endif
TESTGRR_partialImage/$(MODE_DIR)/% : IDE_INCLUDES = -I$(WIND_BASE)/target/h -I$(WIND_BASE)/target/h/wrn/coreip 
TESTGRR_partialImage/$(MODE_DIR)/% : IDE_LIBRARIES = 
TESTGRR_partialImage/$(MODE_DIR)/% : PROJECT_TYPE = DKM
TESTGRR_partialImage/$(MODE_DIR)/% : DEFINES = 
TESTGRR_partialImage/$(MODE_DIR)/% : EXPAND_DBG = 0
TESTGRR_partialImage/$(MODE_DIR)/% : VX_CPU_FAMILY = ppc
TESTGRR_partialImage/$(MODE_DIR)/% : CPU = PPC32
TESTGRR_partialImage/$(MODE_DIR)/% : TOOL_FAMILY = diab
TESTGRR_partialImage/$(MODE_DIR)/% : TOOL = diab
TESTGRR_partialImage/$(MODE_DIR)/% : TOOL_PATH = 
TESTGRR_partialImage/$(MODE_DIR)/% : CC_ARCH_SPEC = -tPPCFH:vxworks63 -Xstmw-slow -DPPC32_fp60x
TESTGRR_partialImage/$(MODE_DIR)/% : LIBPATH = 
TESTGRR_partialImage/$(MODE_DIR)/% : LIBS = 
TESTGRR_partialImage/$(MODE_DIR)/% : OBJ_DIR := TESTGRR_partialImage/$(MODE_DIR)

TESTGRR_partialImage/$(MODE_DIR)/Objects/TESTGRR/RoboDemo.o : C:/WindRiver/frc/frc/workspace/TESTGRR/RoboDemo.cpp
	$(TRACE_FLAG)if [ ! -d "`dirname "$@"`" ]; then mkdir -p "`dirname "$@"`"; fi;echo "building $@"; $(TOOL_PATH)dcc $(DEBUGFLAGS_C++-Compiler) $(CC_ARCH_SPEC) -W:c:,-Xclib-optim-off -Xansi -Xlocal-data-area-static-only -Xforce-declarations  -Xmake-dependency=0xd $(ADDED_C++FLAGS) $(IDE_INCLUDES) $(ADDED_INCLUDES) -DCPU=$(CPU) -DTOOL_FAMILY=$(TOOL_FAMILY) -DTOOL=$(TOOL) -D_WRS_KERNEL   $(DEFINES) -o "$@" -c "$<"


OBJECTS_TESTGRR_partialImage = TESTGRR_partialImage/$(MODE_DIR)/Objects/TESTGRR/RoboDemo.o

TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage.o : $(OBJECTS_TESTGRR_partialImage)
	$(TRACE_FLAG)if [ ! -d "`dirname "$@"`" ]; then mkdir -p "`dirname "$@"`"; fi;echo "building $@"; $(TOOL_PATH)dld -tPPCFH:vxworks63 -X -r5  -o "$@" $(OBJECTS_TESTGRR_partialImage) $(LIBPATH) $(LIBS) $(ADDED_LIBPATH) $(ADDED_LIBS)  && if [ "$(EXPAND_DBG)" = "1" ]; then plink "$@";fi

TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage_compile_file : $(FILE) ;

_clean :: TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage_clean

TESTGRR_partialImage/$(MODE_DIR)/TESTGRR_partialImage_clean : 
	$(TRACE_FLAG)if [ -d "TESTGRR_partialImage" ]; then cd "TESTGRR_partialImage"; rm -rf $(MODE_DIR); fi

DEP_FILES := TESTGRR_partialImage/$(MODE_DIR)/Objects/TESTGRR/RoboDemo.d
-include $(DEP_FILES)



-include $(PRJ_ROOT_DIR)/*.makefile

-include *.makefile

main_all : external_build $(PROJECT_TARGETS)
	@echo "make: built targets of `pwd`"

# entry point for extending the build
external_build ::
	@echo ""

# main entry point for pre processing prior to the build
pre_build :: $(PRE_BUILD_STEP) generate_sources
	@echo ""

# entry point for generating sources prior to the build
generate_sources ::
	@echo ""

# main entry point for post processing after the build
post_build :: $(POST_BUILD_STEP) deploy_output
	@echo ""

# entry point for deploying output after the build
deploy_output ::
	@echo ""

clean :: external_clean $(CLEAN_STEP) _clean

# entry point for extending the build clean
external_clean ::
	@echo ""
