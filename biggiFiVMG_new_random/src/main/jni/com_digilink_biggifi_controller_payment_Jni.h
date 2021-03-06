/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_bagelplay_controller_payment_Jni */


#include <math.h>
#include <poll.h>
#include <stdio.h>
#include <stdarg.h>
#include <fcntl.h>
#include <errno.h>
#include <netdb.h>
#include <stdint.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/timeb.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <linux/fb.h>
#include <sys/types.h>
#include <linux/tcp.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <linux/input.h>
#include <netinet/ip_icmp.h>

 
#include <net/if_arp.h>;
#include <arpa/inet.h>;


#ifndef _Included_com_bagelplay_controller_payment_Jni
#define _Included_com_bagelplay_controller_payment_Jni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_bagelplay_controller_payment_Jni
 * Method:    getAppID
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_bagelplay_controller_payment_Jni_getAppID
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_bagelplay_controller_payment_Jni
 * Method:    getAppKey
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_bagelplay_controller_payment_Jni_getAppKey
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_bagelplay_controller_payment_Jni
 * Method:    getProductPrice
 * Signature: (I)[[B
 */
JNIEXPORT jobjectArray JNICALL Java_com_bagelplay_controller_payment_Jni_getProductPrice
  (JNIEnv *, jclass, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
