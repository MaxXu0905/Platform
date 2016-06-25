#if !defined(_JNI_UTILS_H_)
#define _JNI_UTILS_H_

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <time.h>
#include <math.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <libgen.h>
#include <regex.h>
#include <dirent.h>
#include <netdb.h>
#include <fcntl.h>
#include <string.h>

#include <jni.h>

extern const char * getString(JNIEnv *env, jstring str);
extern jstring newString(JNIEnv *env, const char *str);
extern const char ** getStringArray(JNIEnv *env, jobjectArray objectArray);
extern jobjectArray newStringArray(JNIEnv *env, const char **array);
extern const int * getIntArray(JNIEnv *env, jintArray intArray);
extern jintArray newIntArray(JNIEnv *env, const int *array);

#endif
