package tel.discord.rtab.events;

import tel.discord.rtab.GameController;
import tel.discord.rtab.board.Board;
import tel.discord.rtab.board.Boost;

public class BoostMagnet implements EventSpace
{
	@Override
	public String getName()
	{
		return "Boost Magnet";
	}

	@Override
	public void execute(GameController game, int player)
	{
		//Get the total boost in play
		int totalBoost = 0;
		for(int i=0; i < game.players.size(); i++)
		{
			//If they aren't the current player, add their boost to the pile
			if(i != player)
			{
				totalBoost += (game.players.get(i).booster - 100)/2;
				game.players.get(i).booster -= (game.players.get(i).booster - 100)/2;
			}
		}
		if(totalBoost != 0)
		{
			//And give it to the current player
			game.channel.sendMessage("It's a **Boost Magnet**, you get half of everyone's boost!").queue();
			game.players.get(player).addBooster(totalBoost);
		}
		else
		{
			//No boost in play? BACKUP PLAN
			game.channel.sendMessage("It's a **Boost Magnet**, but there's no boost to steal...").queue();
			try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
			game.channel.sendMessage("So you can have this instead.").queue();
			try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
			game.awardBoost(player, Board.generateSpaces(1, game.players.size(), Boost.values()).get(0));
		}
	}

}