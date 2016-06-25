#include <stdlib.h>
#include <string.h>

void * ailk_malloc(size_t __size) {
	long *ptr = (long *) malloc(__size + sizeof(long));
	*ptr = __size;
	ptr++;
	return ptr;
}

void * ailk_calloc(size_t __nmemb, size_t __size) {
	size_t total = __nmemb * __size;
	long *ptr = (long *) malloc(total + sizeof(long));
	*ptr = total;
	ptr++;
	memset(ptr, '\0', total);
	return ptr;
}

void * ailk_realloc(void *__ptr, size_t __size) {
	long *ptr = (long *) realloc((char *) __ptr - sizeof(long), __size + sizeof(long));
	*ptr = __size;
	ptr++;
	return ptr;
}

void ailk_free(void *__ptr) {
	free((char *) __ptr - sizeof(long));
}

#define malloc(__size) ailk_malloc(__size)
#define calloc(__nmemb, __size) ailk_calloc(__nmemb, __size)
#define realloc(__ptr, __size) ailk_realloc(__ptr, __size)
#define free(__ptr) ailk_free(__ptr)