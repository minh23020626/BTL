#include "Game_Base.h"
#include "utils.h"
#include "LoadText.h"
#include "Button.h"
#include "Player.h"
#include "enemy.h"

const std::string LAYER[BACKGROUND_LAYER] = {
	"img//background//1.png",
	"img//background//2.png",
	"img//background//3.png",
	"img//background//4.png",
	"img//background//5.png",
};

SDL_Window* gWindow = nullptr;
SDL_Renderer* gRenderer = nullptr;
SDL_Color textColor = { 0, 0, 0 };
TTF_Font* gFont = nullptr;
Mix_Music* gMusic = nullptr;
Mix_Music* gMenuMusic = nullptr;
Mix_Chunk* gClick = nullptr;
Mix_Chunk* gJump = nullptr;
Mix_Chunk* gLose = nullptr;

SDL_Rect gPlayButton[BUTTON_TOTAL];
SDL_Rect gHelpButton[BUTTON_TOTAL];
SDL_Rect gExitButton[BUTTON_TOTAL];
SDL_Rect gBackButton[BUTTON_TOTAL];
SDL_Rect gPauseButton[BUTTON_TOTAL];
SDL_Rect gContinueButton[BUTTON_TOTAL];
SDL_Rect gPlayAgainButton[BUTTON_TOTAL];
SDL_Rect gPlayerClips[RUNNING_FRAMES];
SDL_Rect gEnemyClips[FLYING_FRAMES];

LoadText gMenuText;
LoadText gInstructionText;
LoadText gBackgroundText[BACKGROUND_LAYER];
LoadText gPlayerText;
LoadText gGroundText;
LoadText gPlayButtonText;
LoadText gHelpButtonText;
LoadText gExitButtonText;
LoadText gBackButtonText;
LoadText gPauseButtonText;
LoadText gContinueButtonText;
LoadText gLoseText;
LoadText gText1Text;
LoadText gScoreText;
LoadText gText2Text;
LoadText gHighScoreText;

Button PlayButton(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY);
Button HelpButton(HELP_BUTTON_POSX, HELP_BUTTON_POSY);
Button ExitButton(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY);
Button BackButton(BACK_BUTTON_POSX, BACK_BUTTON_POSY);
Button PauseButton(PAUSE_BUTTON_POSX, PAUSE_BUTTON_POSY);
Button ContinueButton(CONTINUE_BUTTON_POSX, CONTINUE_BUTTON_POSY);

player Player;

int main(int argc, char* argv[]) {
	if (!Init()) {
		printf("ko khoi tao dc \n");
	}
	else {
		if (!LoadMedia()) {
			printf("ko load dc media \n");
		}
		else {
			bool Quit_Menu = false;
			bool Play_Again = false;

			Mix_PlayMusic(gMenuMusic, IS_REPEATITIVE);
			while (!Quit_Menu) {
				SDL_Event e_mouse;
				while (SDL_PollEvent(&e_mouse) != 0) {
					if (e_mouse.type == SDL_QUIT) {
						Quit_Menu = true;
					}
					bool Quit_Game = false;
					HandlePlayButton(&e_mouse, PlayButton, Quit_Menu, Play_Again, gClick);
					HandleHelpButton(&e_mouse, gBackButton, HelpButton, BackButton, gInstructionText, gBackButtonText, gRenderer, Quit_Game, gClick);
					HandleExitButton(&e_mouse, ExitButton, Quit_Menu, gClick);
					if (Quit_Game == true) {
						return 0;
					}
				}
				gMenuText.Render(0, 0, gRenderer);
				SDL_Rect* currentClip_Play = &gPlayButton[PlayButton.currentSprite];
				PlayButton.Render(currentClip_Play, gRenderer, gPlayButtonText);

				SDL_Rect* currentClip_Help = &gHelpButton[HelpButton.currentSprite];
				HelpButton.Render(currentClip_Help, gRenderer, gHelpButtonText);

				SDL_Rect* currentClip_Exit = &gExitButton[ExitButton.currentSprite];
				ExitButton.Render(currentClip_Help, gRenderer, gExitButtonText);

				SDL_RenderPresent(gRenderer);
			}

			while (Play_Again) {
				srand(time(NULL));
				int time = 0;
				int score = 0;
				int acceleration = 0;
				int frame_Player = 0;
				int frame_Enemy = 0;
				std::string highscore = gHighScoreFromFile("high_score.txt");

				SDL_Event e;
				enemy Enemy1(ON_GROUND_ENEMY);
				enemy Enemy2(ON_GROUND_ENEMY);
				enemy Enemy3(IN_AIR_ENEMY);

				Mix_PlayMusic(gMusic, IS_REPEATITIVE);
				GenerateEnemy(Enemy1, Enemy2, Enemy3, gEnemyClips, gRenderer);

				int OffsetSpeed_Ground = BASE_OFFSET_SPEED;
				std::vector<double>OffsetSpeed_BackG(BACKGROUND_LAYER, BASE_OFFSET_SPEED);

				bool Quit = false;
				bool Game_State = true;
				while (!Quit) {
					if (Game_State) {
						UpdateGameTimeandScore(time, acceleration, score);
						while (SDL_PollEvent(&e) != 0) {
							if (e.type == SDL_QUIT) {
								Quit = true;
								Play_Again = false;
							}
							HandlePauseButton(&e, gRenderer, gContinueButton, PauseButton, ContinueButton, gContinueButtonText, Game_State, gClick);
							Player.HandleEvent(e, gJump);
						}
						SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);
						SDL_RenderClear(gRenderer);

						RenderScoreBackground(OffsetSpeed_BackG, gBackgroundText, gRenderer);
						RenderScoreGround(OffsetSpeed_Ground, acceleration, gGroundText, gRenderer);

						Player.Move();
						SDL_Rect* currentClip_Player = nullptr;
						if (Player.OnGround()) {
							currentClip_Player = &gPlayerClips[frame_Player / SLOW_FRAME_PLAYER];
							Player.Render(currentClip_Player, gRenderer, gPlayerText);
						}
						else {
							currentClip_Player = &gPlayerClips[0];
							Player.Render(currentClip_Player, gRenderer, gPlayerText);
						}

						Enemy1.Move(acceleration);
						Enemy1.Render(gRenderer);

						Enemy2.Move(acceleration);
						Enemy2.Render(gRenderer);

						SDL_Rect* currentClip_Enemy = &gEnemyClips[frame_Enemy / SLOW_FRAME_ENEMY];
						Enemy3.Move(acceleration);
						Enemy3.Render(gRenderer, currentClip_Enemy);

						SDL_Rect* currentClip_Pause = &gPauseButton[PauseButton.currentSprite];
						PauseButton.Render(currentClip_Pause, gRenderer, gPauseButtonText);

						DrawPlayerScore(gText1Text, gScoreText, textColor, gRenderer, gFont, score);
						DrawPlayerHighScore(gText2Text, gHighScoreText, textColor, gRenderer, gFont, highscore);

						if (CheckEnemyColission(Player, Enemy1, Enemy2, Enemy3, currentClip_Player, currentClip_Enemy)) {
							Mix_PauseMusic();
							Mix_PlayChannel(MIX_CHANNEL, gLose, NOT_REPEATITIVE);
							UpdateHighscore("high_score.txt", score, highscore);
							Quit = true;
						}

						SDL_RenderPresent(gRenderer);

						ControlPlayerFrame(frame_Player);
						ControlEnemyFrame(frame_Enemy);
					}
				}
				DrawEndGameSelection(gLoseText, &e, gRenderer, Play_Again);
				if (!Play_Again) {
					Enemy1.~enemy();
					Enemy2.~enemy();
					Enemy3.~enemy();
				}
			}
		}
	}
	close();
	return 0;
}

bool Init() {
	bool success = true;

	if (SDL_Init(SDL_INIT_VIDEO | SDL_INIT_VIDEO) < 0)
	{
		LogError("ko the khoi tao SDL.", SDL_ERROR);
		success = false;
	}
	else
	{
		if (!SDL_SetHint(SDL_HINT_RENDER_SCALE_QUALITY, "1"))
		{
			std::cout << "Warning: Linear texture filtering not enabled!";
		}

		gWindow = SDL_CreateWindow(WINDOW_TITLE.c_str(), SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED,
			SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN);
		if (gWindow == NULL)
		{
			LogError("ko tao dc window", SDL_ERROR);
			success = false;
		}
		else
		{
			gRenderer = SDL_CreateRenderer(gWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
			if (gRenderer == NULL)
			{
				LogError("ko tao dc renderer", SDL_ERROR);
				success = false;
			}
			else
			{
				SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);

				int imgFlags = IMG_INIT_PNG;
				if (!(IMG_Init(imgFlags) & imgFlags))
				{
					LogError("khong khoi tao dc SDL_image", IMG_ERROR);
					success = false;
				}

				if (Mix_OpenAudio(44100, MIX_DEFAULT_FORMAT, 2, 2048) < 0)
				{
					printf("khong khoi tao am thanh", Mix_GetError());
					success = false;
				}

				if (TTF_Init() == -1)
				{
					printf("SDL_ttf khong dc khoi tao", TTF_GetError());
					success = false;
				}
			}
		}
	}

	return success;
}

bool LoadMedia() {
	bool success = true;

	gMusic = Mix_LoadMUS("sound//game_sound.wav");
	if (gMusic == nullptr) {
		LogError("ko load dc nhac nen", MIX_ERROR);
		success = false;
	}
	gMenuMusic = Mix_LoadMUS("sound//menu_sound.wav");
	if (gMenuMusic == nullptr)
	{
		LogError("ko load dc nhac menu", MIX_ERROR);
		success = false;
	}
	gClick = Mix_LoadWAV("sound//click_sound.wav");
	if (gClick == nullptr)
	{
		LogError("ko load dc am click", MIX_ERROR);
		success = false;
	}
	gJump = Mix_LoadWAV("sound//jump_sound.wav");
	if (gJump == nullptr)
	{
		LogError("ko load dc hieu ung nhay", MIX_ERROR);
		success = false;
	}
	gLose = Mix_LoadWAV("sound//lose_sound.wav");
	if (gLose == nullptr)
	{
		LogError("ko load dc nhac thua", MIX_ERROR);
		success = false;
	}
	else
	{
		gFont = TTF_OpenFont("font//font_game.ttf", 28);
		if (gFont == NULL)
		{
			LogError("ko load dc font", MIX_ERROR);
			success = false;
		}
		else
		{
			if (!gText1Text.LoadFromRenderedText("Your score: ", gFont, textColor, gRenderer))
			{
				std::cout << "ko render text1 texture" << std::endl;
				success = false;
			}

			if (!gText2Text.LoadFromRenderedText("High score: ", gFont, textColor, gRenderer))
			{
				std::cout << "ko render text2 texture" << std::endl;
				success = false;
			}

			if (!gMenuText.LoadFromFile("img//background//bkg_g.png", gRenderer))
			{
				std::cout << "Ko load duoc anh" << std::endl;
				success = false;
			}

			if (!gInstructionText.LoadFromFile("img//background//instruction.png", gRenderer))
			{
				std::cout << "ko load duoc anh" << std::endl;
				success = false;
			}

			if (!gPlayButtonText.LoadFromFile("img//button//play_button.png", gRenderer))
			{
				std::cout << "khong load duoc nut play" << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gPlayButton[i].x = 150 * i;
					gPlayButton[i].y = 0;
					gPlayButton[i].w = 150;
					gPlayButton[i].h = 98;
				}
			}

			if (!gHelpButtonText.LoadFromFile("img//button//help_button.png", gRenderer))
			{
				std::cout << "ko load dc help_button image" << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gHelpButton[i].x = 150 * i;
					gHelpButton[i].y = 0;
					gHelpButton[i].w = 150;
					gHelpButton[i].h = 98;
				}
			}

			if (!gBackButtonText.LoadFromFile("img//button//back_button.png", gRenderer))
			{
				std::cout << "ko load back_button image" << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gBackButton[i].x = 100 * i;
					gBackButton[i].y = 0;
					gBackButton[i].w = 100;
					gBackButton[i].h = 78;
				}
			}

			if (!gExitButtonText.LoadFromFile("img//button//exit_button.png", gRenderer))
			{
				std::cout << "ko load exit_button image" << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gExitButton[i].x = 150 * i;
					gExitButton[i].y = 0;
					gExitButton[i].w = 150;
					gExitButton[i].h = 98;
				}
			}

			if (!gPauseButtonText.LoadFromFile("img//button//pause_button.png", gRenderer))
			{
				std::cout << "ko load pause_button image " << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gPauseButton[i].x = 22 * i;
					gPauseButton[i].y = 0;
					gPauseButton[i].w = 22;
					gPauseButton[i].h = 34;
				}
			}

			if (!gContinueButtonText.LoadFromFile("img//button//continue_button.png", gRenderer))
			{
				std::cout << "ko load continue_button image " << std::endl;
				success = false;
			}
			else
			{
				for (int i = 0; i < BUTTON_TOTAL; ++i)
				{
					gContinueButton[i].x = 22 * i;
					gContinueButton[i].y = 0;
					gContinueButton[i].w = 22;
					gContinueButton[i].h = 34;
				}
			}
			for (int i = 0; i < BACKGROUND_LAYER; ++i)
			{
				if (!gBackgroundText[i].LoadFromFile(LAYER[i].c_str(), gRenderer))
				{
					std::cout << "ko load background image" << std::endl;
					success = false;
				}
			}

			if (!gGroundText.LoadFromFile("img//background//ground.png", gRenderer))
			{
				std::cout << "ko load ground image" << std::endl;
				success = false;
			}

			if (!gPlayerText.LoadFromFile("img///char//player.png", gRenderer))
			{
				std::cout << "ko load image." << std::endl;
				success = false;
			}
			else
			{
				gPlayerClips[0].x = 57 * 0;
				gPlayerClips[0].y = 0;
				gPlayerClips[0].w = 57;
				gPlayerClips[0].h = 57;

				gPlayerClips[1].x = 57 * 1;
				gPlayerClips[1].y = 0;
				gPlayerClips[1].w = 57;
				gPlayerClips[1].h = 57;

				gPlayerClips[2].x = 57 * 2;
				gPlayerClips[2].y = 0;
				gPlayerClips[2].w = 57;
				gPlayerClips[2].h = 57;

				gPlayerClips[3].x = 57 * 3;
				gPlayerClips[3].y = 0;
				gPlayerClips[3].w = 57;
				gPlayerClips[3].h = 57;

				gPlayerClips[4].x = 57 * 4;
				gPlayerClips[4].y = 0;
				gPlayerClips[4].w = 57;
				gPlayerClips[4].h = 57;

				gPlayerClips[5].x = 57 * 5;
				gPlayerClips[5].y = 0;
				gPlayerClips[5].w = 57;
				gPlayerClips[5].h = 57;
			}

			if (!gLoseText.LoadFromFile("img//background//lose.png", gRenderer))
			{
				std::cout << "ko load lose image." << std::endl;
				success = false;
			}
		}
	}
	return success;
}

void close()
{
	gMenuText.Free();
	gInstructionText.Free();
	gPlayerText.Free();
	gGroundText.Free();
	gPlayButtonText.Free();
	gHelpButtonText.Free();
	gExitButtonText.Free();
	gBackButtonText.Free();
	gPauseButtonText.Free();
	gContinueButtonText.Free();
	gLoseText.Free();
	gText1Text.Free();
	gScoreText.Free();
	gText2Text.Free();
	gHighScoreText.Free();

	for (int i = 0; i < BACKGROUND_LAYER; ++i)
	{
		gBackgroundText[i].Free();
	}

	Mix_FreeMusic(gMusic);
	Mix_FreeMusic(gMenuMusic);
	Mix_FreeChunk(gClick);
	Mix_FreeChunk(gLose);
	Mix_FreeChunk(gJump);
	gMusic = nullptr;
	gMenuMusic = nullptr;
	gClick = nullptr;
	gLose = nullptr;
	gJump = nullptr;

	SDL_DestroyRenderer(gRenderer);
	gRenderer = nullptr;

	SDL_DestroyWindow(gWindow);
	gWindow = nullptr;

	IMG_Quit();
	Mix_Quit();
	SDL_Quit();
}