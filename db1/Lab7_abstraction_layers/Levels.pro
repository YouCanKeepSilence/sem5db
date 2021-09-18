TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG+= staticlib
CONFIG += qt

SOURCES += main.cpp \
    cat.cpp \
    dog.cpp \
    olduser.cpp

HEADERS += \
    cat.h \
    middleware.h \
    dog.h \
    abstractcomparator.h \
    dogcomparator.h \
    olduser.h \
    script_api.h
