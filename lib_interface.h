#ifndef LIB_INTERFACE
#define LIB_INTERFACE

#include <vector>

class PrPng {
public:
    PrPng(const char* filename, int angle);

private:

    void decodePicture(const char* filename);
    void rgb2lch(int angle);
    void encodeWithState(const char* filename);

    std::vector<int> img;

    unsigned width;
    unsigned height;
};

#endif //LIB_INTERFACE
