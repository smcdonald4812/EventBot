package eventBotp1;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class Reacted extends MessageReactionAddEvent{

	TimerTriggers tt = new TimerTriggers();
	
	public Reacted(JDA api, long responseNumber, User user, MessageReaction reaction) {
		super(api, responseNumber, user, reaction);
		if(reaction.getChannel().getIdLong() == 422519611939094528L) {
			tt.membListAdd(this.getMember());
		}
	}	
}
