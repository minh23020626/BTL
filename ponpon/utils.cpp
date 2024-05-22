#include "utils.h"

std::string gHighScoreFromFile(std::string path) {
	std::fstream HighScoreFile;
	std::string HighScore;

	HighScoreFile.open(path, std::ios::in);
	HighScoreFile >> HighScore;
        // luu diem cao 
	return HighScore;
}

void UpdateHighscore(std::string path, const int& score, const std::string& old_high_score) {
	int oldHighScore = 0;
	std::fstream HighScoreFile;
	std::string newHighScore;
	std::stringstream ConvertToInt(old_high_score);

	HighScoreFile.open(path, std::ios::out);
       
	ConvertToInt >> oldHighScore;
	if (score > oldHighScore)
	{
		oldHighScore = score;
	}
	newHighScore = std::to_string(oldHighScore);
        // kiem tra highscore va update
	HighScoreFile << newHighScore;
}

int UpdateGameTimeandScore(int& time, int& speed, int& score) {
	if (time == TIME_MAX) {
		speed += SPEED_INCREASEMENT;
	} // khi tg dat gioi han thi tang toc do va cap nhat time
	if (time > TIME_MAX) {
		time = 0;
	}
	if (time % 5 == 0) {
		score += SCORE_INCREASEMENT;
	} // 5 dv tg thi cong diem
	time += TIME_INCREASEMENT;
	return time;
}

void RenderScoreBackground(std::vector<double>& offsetspeed, LoadText(&gBackgroundText)[BACKGROUND_LAYER], SDL_Renderer* gRenderer) {
	std::vector<double>layer_speed;
	layer_speed.push_back(LAYER_1_SPEED);
	layer_speed.push_back(LAYER_2_SPEED);
	layer_speed.push_back(LAYER_3_SPEED);
	layer_speed.push_back(LAYER_4_SPEED);
	layer_speed.push_back(LAYER_5_SPEED);

	for (int i = 0; i < BACKGROUND_LAYER; i++) {
		offsetspeed[i] -= layer_speed[i];
		if (offsetspeed[i] < -gBackgroundText[i].GetWidth()) {
			offsetspeed[i] = 0;
		}  // cuon cac lop den het
		gBackgroundText[i].Render(offsetspeed[i], 0, gRenderer);
		gBackgroundText[i].Render(offsetspeed[i] + gBackgroundText[i].GetWidth(), 0, gRenderer);
		// cap nhat dat lai khi den 0
	}
}

void RenderScoreGround(int& speed, const int acceleration, LoadText gGroundText, SDL_Renderer* gRenderer) {
	speed -= GROUND_SPEED + acceleration; // toc do them gia toc
	if (speed < -gGroundText.GetWidth()) {
		speed = 0;
	} 
	gGroundText.Render(speed, 0, gRenderer);
	gGroundText.Render(speed + gGroundText.GetWidth(), 0, gRenderer); // dat lai khi den gh
}

void HandlePlayButton(SDL_Event* e, Button& PlayButton, bool& QuitMenu, bool& play, Mix_Chunk* gClick) {
	if (e->type == SDL_QUIT) {
		QuitMenu = true;
	}
	if (PlayButton.IsInside(e, COMMON_BUTTON)) {
		switch (e->type) {
		case SDL_MOUSEMOTION:
			PlayButton.currentSprite = BUTTON_MOUSE_OVER;
			break;
		case SDL_MOUSEBUTTONDOWN:
			play = true;
			QuitMenu = true;
			Mix_PlayChannel(MIX_CHANNEL, gClick, 0);
			PlayButton.currentSprite = BUTTON_MOUSE_OVER;
			break;
		}
	}
	else {
		PlayButton.currentSprite = BUTTON_MOUSE_OUT;
	}
}

void HandleHelpButton(SDL_Event* e, SDL_Rect(gBackButton)[BUTTON_TOTAL], Button& HelpButton, Button& BackButton, LoadText gInstructionText,
	LoadText gBackButtonText, SDL_Renderer* gRenderer, bool& Quit_game, Mix_Chunk* gClick) {
	if (HelpButton.IsInside(e, COMMON_BUTTON)) {
		switch (e->type) {
		case SDL_MOUSEMOTION:
			HelpButton.currentSprite = BUTTON_MOUSE_OVER;
			break;
		case SDL_MOUSEBUTTONDOWN:
			HelpButton.currentSprite = BUTTON_MOUSE_OVER;
			Mix_PlayChannel(MIX_CHANNEL, gClick, NOT_REPEATITIVE); // kiem tra toa do event chuot

			bool ReadDone = false; // bien check doc instruction 
			while (!ReadDone) {
				do {
					if (e->type == SDL_QUIT) {
						ReadDone = true;
						Quit_game = true;
						close();
					}
					else if (BackButton.IsInside(e, COMMON_BUTTON)) {
						switch (e->type) {
						case SDL_MOUSEMOTION:
							BackButton.currentSprite = BUTTON_MOUSE_OVER;
							break;
						case SDL_MOUSEBUTTONDOWN:
							BackButton.currentSprite = BUTTON_MOUSE_OVER;
							Mix_PlayChannel(MIX_CHANNEL, gClick, NOT_REPEATITIVE);
							ReadDone = true;
							break;
						}
					}
					else {
						BackButton.currentSprite = BUTTON_MOUSE_OUT;
					}
					gInstructionText.Render(0, 0, gRenderer);
					SDL_Rect* currentClip_Back = &gBackButton[BackButton.currentSprite];
					BackButton.Render(currentClip_Back, gRenderer, gBackButtonText);

					SDL_RenderPresent(gRenderer);
				} while (SDL_PollEvent(e) != 0 && e->type == SDL_MOUSEBUTTONDOWN || e->type == SDL_MOUSEMOTION);
			}
			break;
		}
	}
	else {
		HelpButton.currentSprite = BUTTON_MOUSE_OUT;
	}
}

void HandleExitButton(SDL_Event* e, Button& ExitButton, bool& Quit, Mix_Chunk* gClick) {
	if (ExitButton.IsInside(e, COMMON_BUTTON)) {
		switch (e->type) {
		case SDL_MOUSEMOTION:
			ExitButton.currentSprite = BUTTON_MOUSE_OVER;
			break;
		case SDL_MOUSEBUTTONDOWN:
			Quit = true;
			ExitButton.currentSprite = BUTTON_MOUSE_OVER;
			Mix_PlayChannel(MIX_CHANNEL, gClick, NOT_REPEATITIVE);
			break;
		}
	}
	else {
		ExitButton.currentSprite = BUTTON_MOUSE_OUT;
	}
}

void HandleContinueButton(Button ContinueButton, LoadText gContinueButtonText, SDL_Event* e, SDL_Renderer* gRenderer, SDL_Rect(&gContinueButton)[BUTTON_TOTAL],
	bool& Game_state, Mix_Chunk* gClick) {
	bool Back_Game = false;
	while (!Back_Game) {
		do {
			if (ContinueButton.IsInside(e, SMALL_BUTTON)) {
				switch (e->type) {
				case SDL_MOUSEMOTION:
					ContinueButton.currentSprite = BUTTON_MOUSE_OVER;
					break;
				case SDL_MOUSEBUTTONDOWN:
				{
					ContinueButton.currentSprite = BUTTON_MOUSE_OVER;
					Mix_PlayChannel(MIX_CHANNEL, gClick, NOT_REPEATITIVE);
					Mix_ResumeMusic();
					Game_state = true;
					Back_Game = true;
				}
				break;
				}
			}
			else {
				ContinueButton.currentSprite = BUTTON_MOUSE_OUT;
			}
			SDL_Rect* currentClip_Continue = &gContinueButton[ContinueButton.currentSprite];
			ContinueButton.Render(currentClip_Continue, gRenderer, gContinueButtonText);
			SDL_RenderPresent(gRenderer);
		} while (SDL_WaitEvent(e) != 0 && e->type == SDL_MOUSEBUTTONDOWN || e->type == SDL_MOUSEMOTION);
	}
}

void HandlePauseButton(SDL_Event* e, SDL_Renderer* gRenderer, SDL_Rect(&gContinueButton)[BUTTON_TOTAL], Button& PauseButton, Button ContinueButton,
	LoadText gContinueButtonText, bool& game_state, Mix_Chunk* gClick) {
	if (PauseButton.IsInside(e, SMALL_BUTTON)) {
		switch (e->type) {
		case SDL_MOUSEMOTION:
			PauseButton.currentSprite = BUTTON_MOUSE_OVER;
			break;
		case SDL_MOUSEBUTTONDOWN:
			PauseButton.currentSprite = BUTTON_MOUSE_OVER;
			Mix_PlayChannel(MIX_CHANNEL, gClick, NOT_REPEATITIVE);
			Mix_PauseMusic();
			break;
		case SDL_MOUSEBUTTONUP:
			game_state = false;
			HandleContinueButton(ContinueButton, gContinueButtonText, e, gRenderer, gContinueButton, game_state, gClick);
			break;
		}
	}
	else {
		PauseButton.currentSprite = BUTTON_MOUSE_OUT;
	}
}

void GenerateEnemy(enemy& Enemy1, enemy& Enemy2, enemy& Enemy3, SDL_Rect(&gEnemyClips)[FLYING_FRAMES], SDL_Renderer* gRenderer) {
	Enemy1.LoadFromFile("img//char//cactus.png", gRenderer);
	Enemy2.LoadFromFile("img//char//cactus.png", gRenderer);
	Enemy3.LoadFromFile("img//char//bat.png", gRenderer);
	{
		gEnemyClips[0].x = 43 * 3;
		gEnemyClips[0].y = 0;
		gEnemyClips[0].w = 43;
		gEnemyClips[0].h = 30;

		gEnemyClips[1].x = 43 * 4;
		gEnemyClips[1].y = 0;
		gEnemyClips[1].w = 43;
		gEnemyClips[1].h = 30;

		gEnemyClips[2].x = 43 * 2;
		gEnemyClips[2].y = 0;
		gEnemyClips[2].w = 43;
		gEnemyClips[2].h = 30;

		gEnemyClips[3].x = 43;
		gEnemyClips[3].y = 0;
		gEnemyClips[3].w = 43;
		gEnemyClips[3].h = 30;

		gEnemyClips[4].x = 0;
		gEnemyClips[4].y = 0;
		gEnemyClips[4].w = 43;
		gEnemyClips[4].h = 30;
	}
}

bool CheckColission(player Player, SDL_Rect* player_clip, enemy Enemy, SDL_Rect* enemy_clip) {
	bool collide = false;

	int left_a = Player.gPosX();
	int right_a = Player.gPosX() + player_clip->w;
	int top_a = Player.gPosY();
	int bottom_a = Player.gPosY() + player_clip->h;

	if (Enemy.GetType() == ON_GROUND_ENEMY) {
		const int TRASH_PIXEL_1 = 25; // do gian de dieu chinh va cham cho hop li
		const int TRASH_PIXEL_2 = 30;

		int left_b = Enemy.gPosX();
		int right_b = Enemy.gPosX() + Enemy.gWidth();
		int top_b = Enemy.gPosY();
		int bottom_b = Enemy.gPosY() + Enemy.gHeight();

		if (right_a - TRASH_PIXEL_1 >= left_b && left_a + TRASH_PIXEL_1 <= right_b)
		{
			if (bottom_a - TRASH_PIXEL_2 >= top_b)
			{
				collide = true;
			}
		}
	}
	else {
		const int TRASH_PIXEL_1 = 22;
		const int TRASH_PIXEL_2 = 18;

		int left_b = Enemy.gPosX() + TRASH_PIXEL_1;
		int right_b = Enemy.gPosX() + enemy_clip->w - TRASH_PIXEL_1;
		int top_b = Enemy.gPosY();
		int bottom_b = Enemy.gPosY() + enemy_clip->h - TRASH_PIXEL_2;

		if (right_a >= left_b && left_a <= right_b)
		{
			if (top_a <= bottom_b && top_a >= top_b)
			{
				collide = true;
			}

			if (bottom_a >= bottom_b && bottom_a <= top_b)
			{
				collide = true;
			}
		}
	}
	return collide;
}

bool CheckEnemyColission(player Player, enemy Enemy1, enemy Enemy2, enemy Enemy3, SDL_Rect* player_clip, SDL_Rect* enemy_clip) {
	if (CheckColission(Player, player_clip, Enemy1)) {
		return true;
	}
	if (CheckColission(Player, player_clip, Enemy2)) {
		return true;
	}
	if (CheckColission(Player, player_clip, Enemy3, enemy_clip)) {
		return true;
	}
	return false;
}

void ControlPlayerFrame(int& frame) {
	frame += FRAME_INCREASEMENT;
	if (frame / SLOW_FRAME_PLAYER >= RUNNING_FRAMES) {
		frame = 0;
	}
}

void ControlEnemyFrame(int& frame) {
	frame += FRAME_INCREASEMENT;
	if (frame / SLOW_FRAME_ENEMY >= FLYING_FRAMES) {
		frame = 0;
	}
}

void DrawPlayerScore(LoadText gText, LoadText gScoreText, SDL_Color textColor, SDL_Renderer* gRenderer, TTF_Font* gFont, const int& Score) {
	gText.Render(TEXT_1_POSX, TEXT_1_POSY, gRenderer); // hien thi diem o hai vi tri t1
	if (gScoreText.LoadFromRenderedText(std::to_string(Score), gFont, textColor, gRenderer)) {
		gScoreText.Render(SCORE_POSX, SCORE_POSY, gRenderer);
	}
}

void DrawPlayerHighScore(LoadText gText, LoadText gHighScoreText, SDL_Color textColor, SDL_Renderer* gRenderer, TTF_Font* gFont, const std::string& HighScore) {
	gText.Render(TEXT_2_POSX, TEXT_2_POSY, gRenderer); // hien thi highscore t2
	if (gHighScoreText.LoadFromRenderedText(HighScore, gFont, textColor, gRenderer)) {
		gHighScoreText.Render(HIGH_SCORE_POSX, HIGH_SCORE_POSY, gRenderer);
	}
}

void DrawEndGameSelection(LoadText gLoseText, SDL_Event* e, SDL_Renderer* gRenderer, bool& Play_Again) {
	if (Play_Again) {
		bool End_Game = false;
		while (!End_Game) {
			while (SDL_PollEvent(e) != 0) {
				if (e->type == SDL_QUIT) {
					Play_Again = false;
				}

				if (e->type == SDL_KEYDOWN) {
					switch (e->key.keysym.sym) {
					case SDLK_SPACE:
						End_Game = true;
						break;
					case SDLK_ESCAPE:
						End_Game = true;
						Play_Again = false;
						break;
					}
				}
			}
			gLoseText.Render(0, 0, gRenderer);

			SDL_RenderPresent(gRenderer);
		}
	}
}
