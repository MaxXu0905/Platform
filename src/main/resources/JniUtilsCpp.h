#if !defined(_JNI_UTILS_H_)
#define _JNI_UTILS_H_

#include <cstdio>
#include <cstdlib>
#include <cerrno>
#include <ctime>
#include <cmath>
#include <cctype>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <libgen.h>
#include <string>
#include <vector>
#include <regex.h>
#include <dirent.h>
#include <algorithm>
#include <functional>
#include <netdb.h>
#include <fcntl.h>

#include <string>
#include <vector>
#include <list>
#include <map>
#include <set>
#include <stack>
#include <exception>
#include <iostream>
#include <fstream>
#include <sstream>
#include <iomanip>
#include <memory>
#include <jni.h>

using namespace std;

class JniUtils {
public:
	static auto_ptr<string> getString(JNIEnv *env, jstring str) {
		const char *data = env->GetStringUTFChars(str, NULL);
		if (data == NULL)
			return auto_ptr<string>();

		int len = env->GetStringUTFLength(str);
		auto_ptr<string> result(new string(data, data + len));
		env->ReleaseStringUTFChars(str, data);
		
		return result;
	}
	
	static jstring newString(JNIEnv *env, const string& str) {
		return env->NewStringUTF(str.c_str());
	}
	
	static auto_ptr<vector<string> > getStringArray(JNIEnv *env, jobjectArray objectArray) {
		if (objectArray == NULL)
			return auto_ptr<vector<string> >();

		jsize size = env->GetArrayLength(objectArray);
		auto_ptr<vector<string> > result(new vector<string>());	
		vector<string> *resultArray = result.get();
		
		for (jsize i = 0; i < size; i++) {
			jstring str = reinterpret_cast<jstring>(env->GetObjectArrayElement(objectArray, i));
			
			const char *data = env->GetStringUTFChars(str, NULL);
			if (data == NULL)
				continue;
				
			int len = env->GetStringUTFLength(str);
			resultArray->push_back(string(data, data + len));
			env->ReleaseStringUTFChars(str, data);
		}
		
		return result;
	}
	
	static jobjectArray newStringArray(JNIEnv *env, const vector<string>& array) {
		if (array.empty())
			return NULL;

		jclass claz = env->FindClass("java/lang/String");
		jobjectArray result = env->NewObjectArray(array.size(), claz, 0);

		for (string::size_type i = 0; i < array.size(); i++) {
			jstring str = env->NewStringUTF(array[i].c_str());
			env->SetObjectArrayElement(result, i, str);
		}
		
		return result;
	}
	
	static auto_ptr<vector<int> > getIntArray(JNIEnv *env, jintArray intArray) {
		if (intArray == NULL)
			return auto_ptr<vector<int> >();

		jsize size = env->GetArrayLength(intArray);
		auto_ptr<vector<int> > result(new vector<int>());
		vector<int> *resultArray = result.get();
		resultArray->reserve(size);

		jint *array = env->GetIntArrayElements(intArray, NULL);
		for (jsize i = 0; i < size; i++)
			resultArray->push_back(array[i]);
		env->ReleaseIntArrayElements(intArray, array, 0);
		
		return result;
	}
	
	static jintArray newIntArray(JNIEnv *env, const vector<int>& array) {
		if (array.empty())
			return NULL;

		jintArray result = env->NewIntArray(array.size());
		jint *resultArray = env->GetIntArrayElements(result, NULL);
		for (string::size_type i = 0; i < array.size(); i++)
			resultArray[i] = array[i];
		env->ReleaseIntArrayElements(result, resultArray, 0);

		return result;
	}

};
	
#endif
