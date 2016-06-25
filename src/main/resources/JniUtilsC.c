#include "JniUtils.h"

const char * getString(JNIEnv *env, jstring str) {
	const char *data = (*env)->GetStringUTFChars(env, str, NULL);
	if (data == NULL)
		return NULL;

	int len = (*env)->GetStringUTFLength(env, str);
	char *result = (char *) malloc(len + 1);
	memcpy(result, data, len);
	result[len] = '\0';
	 
	(*env)->ReleaseStringUTFChars(env, str, data);
	
	return (const char *) result;
}

jstring newString(JNIEnv *env, const char *str) {
	if (str == NULL)
		return NULL;

	return (*env)->NewStringUTF(env, str);
}

const char ** getStringArray(JNIEnv *env, jobjectArray objectArray) {
	if (objectArray == NULL)
		return NULL;

	jsize size = (*env)->GetArrayLength(env, objectArray);
	char **result = (char **) malloc(sizeof(char *) * (size + 1));

	int i;
	for (i = 0; i < size; i++) {
		jstring str = (jstring) ((*env)->GetObjectArrayElement(env, objectArray, i));
		
		const char *data = (*env)->GetStringUTFChars(env, str, NULL);
		if (data == NULL)
			continue;
			
		int len = (*env)->GetStringUTFLength(env, str);
		char *item = (char *) malloc(len + 1);
		memcpy(item, data, len + 1);
		result[i] = item;
		(*env)->ReleaseStringUTFChars(env, str, data);
	}
	
	result[size] = NULL;	
	return (const char **) result;
}

jobjectArray newStringArray(JNIEnv *env, const char **array) {
	if (array == NULL)
		return NULL;

	long *ptr = (long *) array;
	size_t size = ptr[-1] / sizeof(char *);
	if (size <= 0 || size >= 0x10000)
		return NULL;

	jclass claz = (*env)->FindClass(env, "java/lang/String");
	jobjectArray result = (*env)->NewObjectArray(env, size, claz, 0);
	int i;
	for (i = 0; i < size; i++) {
		jstring str = (*env)->NewStringUTF(env, array[i]);
		(*env)->SetObjectArrayElement(env, result, i, str);
	}
	
	return result;
}

const int * getIntArray(JNIEnv *env, jintArray intArray) {
	jsize size = (*env)->GetArrayLength(env, intArray);
	int *result = (int *) malloc(sizeof(int) * size);
	
	jint *array = (*env)->GetIntArrayElements(env, intArray, NULL);
	int i;
	for (i = 0; i < size; i++)
		result[i] = array[i];
	(*env)->ReleaseIntArrayElements(env, intArray, array, 0);
	
	return (const int *) result;
}

jintArray newIntArray(JNIEnv *env, const int *array) {
	if (array == NULL)
		return NULL;
	
	long *ptr = (long *) array;
	size_t size = ptr[-1] / sizeof(int);
	if (size <= 0 || size >= 0x10000)
		return NULL;
	
	jintArray result = (*env)->NewIntArray(env, size);
	jint *resultArray = (*env)->GetIntArrayElements(env, result, NULL);
	int i;
	for (i = 0; i < size; i++)
		resultArray[i] = array[i];
	(*env)->ReleaseIntArrayElements(env, result, resultArray, 0);

	return result;
}
