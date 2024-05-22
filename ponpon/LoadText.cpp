#include "LoadText.h"

LoadText::LoadText() {
	mTexture = nullptr;

	mWidth = 0;
	mHeight = 0;
}

LoadText::~LoadText() {
	Free();
}

void LoadText::Free() {
	if (mTexture != nullptr) {
		mTexture = nullptr;
		mWidth = 0;
		mHeight = 0;
	}
}

bool LoadText::LoadFromRenderedText(std::string textureText, TTF_Font* gFont, SDL_Color textColor, SDL_Renderer* gRenderer) {
	Free();

	SDL_Surface* textSurface = TTF_RenderText_Solid(gFont, textureText.c_str(), textColor);
	if (textSurface == NULL) {
		printf("loi o ttf \n", TTF_GetError());
	}
	else {
		mTexture = SDL_CreateTextureFromSurface(gRenderer, textSurface); // l?u text t? sur
		if (mTexture == NULL) {
			printf("loi o tao text from surface \n", SDL_GetError());
		}
		else {
			mWidth = textSurface->w;
			mHeight = textSurface->h;
		}
		SDL_FreeSurface(textSurface);
	}
	return mTexture != NULL;
}

bool LoadText::LoadFromFile(std::string path, SDL_Renderer* gRenderer) {
	Free();

	SDL_Texture* tmpTexture = nullptr;

	SDL_Surface* tmpSurface = IMG_Load(path.c_str());
	if (tmpSurface == nullptr) {
		LogError("ko load anh", IMG_ERROR);
	}
	else {
		SDL_SetColorKey(tmpSurface, SDL_TRUE, SDL_MapRGB(tmpSurface->format, 0, 255, 255));

		tmpTexture = SDL_CreateTextureFromSurface(gRenderer, tmpSurface);
		if (tmpTexture == nullptr) {
			LogError("ko load dc text tu sur \n", SDL_ERROR);
		}
		else {
			mWidth = tmpSurface->w;
			mHeight = tmpSurface->h;
		}
		SDL_FreeSurface(tmpSurface);
	}
	mTexture = tmpTexture;

	return mTexture != nullptr;
}

void LoadText::Render(int x, int y, SDL_Renderer* gRenderer, SDL_Rect* clip) {
	SDL_Rect renderSpace = { x,y,mWidth,mHeight };
	if (clip != nullptr) {
		renderSpace.w = clip->w;
		renderSpace.h = clip->h;
	}
	SDL_RenderCopy(gRenderer, mTexture, clip, &renderSpace); // animation load
}

int LoadText::GetWidth() {
	return mWidth;
}
int LoadText::GetHeight() {
	return mHeight;
}