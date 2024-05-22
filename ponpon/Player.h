#pragma once

#include "LoadText.h"

#define JUMP 1
#define FALL 2
#define RUN 0

class player {
public:
	static const int JUMP_SPEED = 8;
	static const int FALL_SPEED = 8;

	player();
	bool OnGround();
	void HandleEvent(SDL_Event& e, Mix_Chunk* gJump);
	void Move();
	void Render(SDL_Rect* currentClip, SDL_Renderer* gRenderer, LoadText gPlayerText);
	int gPosX();
	int gPosY();
private:
	int posX, posY;
	int status;
};