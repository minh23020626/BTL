#pragma once

#include "Game_Base.h"
#include "LoadText.h"

class Button {
public:
	ButtonSprite currentSprite;
	Button();
	Button(int x, int y);
	void SetPosition(int x, int y);
	bool IsInside(SDL_Event* e, int size);
	void Render(SDL_Rect* currentclip, SDL_Renderer* gRenderer, LoadText gButtonTexture);
private:
	SDL_Point position; // bi?u di?n 1 ?i?m 
};