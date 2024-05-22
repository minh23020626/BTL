#pragma once

#include "Game_Base.h"

class LoadText
{
public:
	LoadText();
	~LoadText();
	void Free();

	bool LoadFromRenderedText(std::string textureText, TTF_Font* gFont, SDL_Color textColor, SDL_Renderer* gRenderer);
	// load phông ch? t? TTf_font dùng cho text và màu lên render
	bool LoadFromFile(std::string path, SDL_Renderer* gRenderer); // load t? link

	void Render(int x, int y, SDL_Renderer* gRenderer, SDL_Rect* clip = nullptr);

	int GetWidth();
	int GetHeight();

private:
	SDL_Texture* mTexture;

	int mWidth;
	int mHeight;

};