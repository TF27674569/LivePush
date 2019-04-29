

cmake_minimum_required(VERSION 3.4.1)

add_library(ndk_push SHARED ndk_push.cpp queue.cpp)

find_library( log-lib log)

#引入头文件
include_directories(${CMAKE_SOURCE_DIR}/include/faac)
include_directories(${CMAKE_SOURCE_DIR}/include/rtmp)
include_directories(${CMAKE_SOURCE_DIR}/include/x264)
include_directories(${CMAKE_SOURCE_DIR}/include)

#引入第三方so
link_directories(${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/)

add_library( faac
        STATIC    #实在找不到在哪个shell改版本号
        IMPORTED )
set_target_properties( faac
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libfaac.a)

add_library( rtmp
        SHARED
        IMPORTED )
set_target_properties( rtmp
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/librtmp.so)


add_library( x264
        SHARED
        IMPORTED )
set_target_properties( x264
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libx264.so)

target_link_libraries(
        ndk_push
        faac
        rtmp
        x264
        ${log-lib})