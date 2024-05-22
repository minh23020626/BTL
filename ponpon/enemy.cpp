#include "enemy.h"

enemy::enemy(int _type) {
	posX = 0;
	posY = 0;

	eWidth = 0;
	eHeight = 0;

	type = _type;
	if (type == IN_AIR_ENEMY) {
		posX = rand() % (SCREEN_WIDTH + ENEMY_POSITION_RANGE) + SCREEN_WIDTH; // rand ra vi tri theo chieu ngang trog pham vi 
		posY = rand() % (ENEMY_MAX_HEIGHT - ENEMY_MIN_HEIGHT + 1) + ENEMY_MIN_HEIGHT; // +1 de lay ca vi tri max va min
	}
	if (type == ON_GROUND_ENEMY) {
		posX = rand() % (SCREEN_WIDTH + ENEMY_POSITION_RANGE) + SCREEN_WIDTH;
		posY = GROUND - 8;
	}

	EnemyText = nullptr;
}
enemy::~enemy() {
	posX = 0;
	posY = 0;

	eWidth = 0;
	eHeight = 0;

	type = 0;
	if (EnemyText != nullptr) EnemyText = nullptr;
}

void enemy::LoadFromFile(std::string path, SDL_Renderer* gRenderer) {
	SDL_Texture* tmpText = nullptr;

	SDL_Surface* tmpSurface = IMG_Load(path.c_str());
	if (tmpSurface == nullptr) {
		LogError("loi ko tai dc surface", IMG_ERROR);
	}
	else {
		SDL_SetColorKey(tmpSurface, SDL_TRUE, SDL_MapRGB(tmpSurface->format, 0, 255, 255));

		tmpText = SDL_CreateTextureFromSurface(gRenderer, tmpSurface);
		if (tmpText == nullptr) {
			LogError("ko the tao text tu surface", SDL_ERROR);
		}
		else {
			eWidth = tmpSurface->w;
			eHeight = tmpSurface->h;
		}
		SDL_FreeSurface(tmpSurface);
	}
	EnemyText = tmpText;
}

void enemy::Move(const int& acceleration) {
	posX += -(ENEMY_SPEED + acceleration);
	if (posX + MAX_ENEMY_WIDTH < 0) {
		posX = rand() % (SCREEN_WIDTH + ENEMY_POSITION_RANGE) + SCREEN_WIDTH;
		if (type == IN_AIR_ENEMY) {
			posY = rand() % (ENEMY_MAX_HEIGHT - ENEMY_MIN_HEIGHT + 1) + ENEMY_MIN_HEIGHT;
		}
	}
}

void enemy::Render(SDL_Renderer* gRenderer, SDL_Rect* currentClip) {
	SDL_Rect renderSpace = { posX,posY,eWidth,eHeight };
	if (currentClip != nullptr) {
		renderSpace.w = currentClip->w;
		renderSpace.h = currentClip->h;
	}
	SDL_RenderCopy(gRenderer, EnemyText, currentClip, &renderSpace); // curren la vi tri text muon copy con renderspace là kich thuoc va vi tri render copy

}

int enemy::GetType() {
	if (type == IN_AIR_ENEMY) {
		return IN_AIR_ENEMY;
	}
	else {
		return ON_GROUND_ENEMY;
	}
}

int enemy::GetSpeed(const int& acceleration) {
	return ENEMY_SPEED + acceleration;
}

int enemy::gPosX() {
	return posX;
}
int enemy::gPosY() {
	return posY;
}
int enemy::gHeight() {
	return eHeight;
}
int enemy::gWidth() {
	return eWidth;
}
