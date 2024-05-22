#pragma once

#include <SDL.h>
#include <SDL_image.h>
#include <SDL_ttf.h>
#include <SDL_mixer.h>
#include <iostream>
#include <fstream>
#include <sstream> // dùng thao tác v?i chu?i nh? hàm stringstream
#include <string>
#include <vector>
#include <ctime> // thao tác v?i th?i gian

#define SDL_ERROR 1
#define IMG_ERROR 2
#define MIX_ERROR 3
#define TTF_ERROR 4

#define MIX_CHANNEL -1

#define TIME_MAX 1000
#define GROUND 430
#define MAX_HEIGHT 250

#define BASE_OFFSET_SPEED 0

#define SPEED_INCREASEMENT 2 // t?c ?? t?ng
#define SCORE_INCREASEMENT 1
#define TIME_INCREASEMENT 1 // tg t?ng
#define FRAME_INCREASEMENT 1 // khung t?ng

#define GROUND_SPEED 5 // t?c ?? m?t ??t
#define ENEMY_SPEED 5 // t?c ?? quái
#define MAX_ENEMY_WIDTH 100 // ?? to

#define IN_AIR_ENEMY 1 // quái ko
#define ON_GROUND_ENEMY 0 // quái ??t

#define SLOW_FRAME_PLAYER 4
#define SLOW_FRAME_ENEMY 4

#define IS_REPEATITIVE -1 // nh?c l?i 
#define NOT_REPEATITIVE 0

#define SMALL_BUTTON 1 // nút
#define COMMON_BUTTON 2

const std::string WINDOW_TITLE = "ponpon";

const int SCREEN_WIDTH = 928;
const int SCREEN_HEIGHT = 522;

const int COMMON_BUTTON_WIDTH = 150;
const int COMMON_BUTTON_HEIGHT = 98;
const int SMALL_BUTTON_WIDTH = 22;
const int SMALL_BUTTON_HEIGHT = 34; // kích th??c các nút

const int PLAY_BUTTON_POSX = 389;
const int PLAY_BUTTON_POSY = 186;
const int HELP_BUTTON_POSX = 389;
const int HELP_BUTTON_POSY = 293;
const int EXIT_BUTTON_POSX = 389;
const int EXIT_BUTTON_POSY = 402;
const int BACK_BUTTON_POSX = 31;
const int BACK_BUTTON_POSY = 29;
const int PAUSE_BUTTON_POSX = 31;
const int PAUSE_BUTTON_POSY = 29;
const int CONTINUE_BUTTON_POSX = 31;
const int CONTINUE_BUTTON_POSY = 29;

const int TEXT_1_POSX = 670;
const int TEXT_1_POSY = 20;
const int TEXT_2_POSX = 670;
const int TEXT_2_POSY = 80;
const int SCORE_POSX = 830;
const int SCORE_POSY = 20;
const int HIGH_SCORE_POSX = 830; // kích th??c các hi?n th?
const int HIGH_SCORE_POSY = 80;

const double LAYER_1_SPEED = 0.0;
const double LAYER_2_SPEED = 0.25;
const double LAYER_3_SPEED = 0.5;
const double LAYER_4_SPEED = 0.75;
const double LAYER_5_SPEED = 1.0; // t?c ?? các l?p 

const int RUNNING_FRAMES = 6;
const int FLYING_FRAMES = 5;
const int BACKGROUND_LAYER = 5;

enum ButtonSprite
{
	BUTTON_MOUSE_OUT = 0,
	BUTTON_MOUSE_OVER = 1,
	BUTTON_TOTAL = 2
}; // enum t?p h?p các hàng sô li?t kê

void LogError(std::string msg, int error_code = SDL_ERROR);
