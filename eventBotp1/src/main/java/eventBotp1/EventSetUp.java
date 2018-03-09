package eventBotp1;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class EventSetUp {
	
	TimerTriggers timeT = new TimerTriggers();
	private enum repeatTime {
		yearly, monthly, weekly, daily, once, never;
	}

	//repeats eventName date 
	public void repeatCheck(String[] s, MessageReceivedEvent msg) {
		String repeats = s[0].trim();
		if(enumContains(repeats)) {
			timeT.setTriggers(s, msg);
		} else {
			msg.getChannel().sendMessage("The event can only repeat yearly, monthly, weekly, daily, once, or never").queue();
		}
	}
	
	private static boolean enumContains(String test) {

	    for (repeatTime c : repeatTime.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}

}
