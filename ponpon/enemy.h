#pragma once

#include "LoadText.h"

#define ENEMY_MAX_HEIGHT 300
#define ENEMY_MIN_HEIGHT 330

#define ENEMY_POSITION_RANGE 250
#define ENEMY1_RANGE 100
#define ENEMY2_RANGE 350
#define ENEMY3_RANGE 500

class enemy {
public:
	enemy(int _type = 0); // loai enemy (tren ko , duoi dat ...)
	~enemy();

	void LoadFromFile(std::string path, SDL_Renderer* gRenderer);
	void Move(const int& acceleration);
	void Render(SDL_Renderer* gRenderer, SDL_Rect* currentClip = nullptr);
	int GetType();
	int GetSpeed(const int& acceleration);
	int gPosX();
	int gPosY();
	int gWidth();
	int gHeight();
private:
	int posX, posY;
	int eWidth, eHeight;
	int type;
	SDL_Texture* EnemyText;

};