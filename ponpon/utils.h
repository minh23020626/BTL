#pragma once
// tien ich
#include "Game_Base.h"
#include "LoadText.h"
#include "Button.h"
#include "enemy.h"
#include "Player.h"

bool Init();
bool LoadMedia();
void close();

std::string gHighScoreFromFile(std::string path);

void UpdateHighscore(std::string path, const int& score, const std::string& old_high_score);
int UpdateGameTimeandScore(int& time, int& speed, int& score);
void RenderScoreBackground(std::vector<double>& offsetspeed, LoadText(&gBackgroundText)[BACKGROUND_LAYER], SDL_Renderer* gRenderer);
void RenderScoreGround(int& speed, const int acceleration, LoadText gGroundText, SDL_Renderer* gRenderer);
void HandlePlayButton(SDL_Event* e, Button& PlayButton, bool& QuitMenu, bool& Play, Mix_Chunk* gClick);
void HandleHelpButton(SDL_Event* e, SDL_Rect(gBackButton)[BUTTON_TOTAL], Button& HelpButton, Button& BackButton, LoadText gInstructionText,
	LoadText gBackButtonText, SDL_Renderer* gRenderer, bool& Quit_game, Mix_Chunk* gClick);
void HandleExitButton(SDL_Event* e, Button& ExitButton, bool& Quit, Mix_Chunk* gClick);
void HandleContinueButton(Button ContinueButton, LoadText gContinueButtonText, SDL_Event* e, SDL_Renderer* gRenderer, SDL_Rect(&gContinueButton)[BUTTON_TOTAL],
	bool& Game_state, Mix_Chunk* gClick);

void HandlePauseButton(SDL_Event* e, SDL_Renderer* gRenderer, SDL_Rect(&gContinueButton)[BUTTON_TOTAL], Button& PauseButton, Button ContinueButton,
	LoadText gContinueButtonText, bool& game_state, Mix_Chunk* gClick);
void GenerateEnemy(enemy& Enemy1, enemy& Enemy2, enemy& Enemy3, SDL_Rect(&gEnemyClips)[FLYING_FRAMES], SDL_Renderer* gRenderer);
bool CheckColission(player Player, SDL_Rect* player_clip, enemy Enemy, SDL_Rect* enemy_clip = nullptr);
bool CheckEnemyColission(player Player, enemy Enemy1, enemy Enemy2, enemy Enemy3, SDL_Rect* player_clip, SDL_Rect* enemy_clip = nullptr);
void ControlPlayerFrame(int& frame);
void ControlEnemyFrame(int& frame);
void DrawPlayerScore(LoadText gText, LoadText gScoreText, SDL_Color textColor, SDL_Renderer* gRenderer, TTF_Font* gFont, const int& score);
void DrawPlayerHighScore(LoadText gText, LoadText gHighScoreText, SDL_Color textColor, SDL_Renderer* gRenderer, TTF_Font* gFont, const std::string& HighScore);
void DrawEndGameSelection(LoadText gLoseText, SDL_Event* e, SDL_Renderer* gRenderer, bool& Play_Again);