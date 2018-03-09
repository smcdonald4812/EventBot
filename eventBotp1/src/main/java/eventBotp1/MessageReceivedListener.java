package eventBotp1;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter{
	
	
	public void onMessageReceived(MessageReceivedEvent e) {
		//check for prefix
		if(!e.getMessage().getContentRaw().startsWith(eventBot.prefix)) return;
		//check channel type
		if(e.getChannelType() == ChannelType.PRIVATE) {
			System.out.println(String.format("[DM] %s#%s: %s", e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getContentRaw()));
		} else {
			System.out.println(String.format("[%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getContentRaw()));
		}
		
		//shortcuts for common parts of message
		String command = e.getMessage().getContentStripped().replace(eventBot.prefix, "").split(" ")[0];
		String args = e.getMessage().getContentRaw().replace(eventBot.prefix, "").replace(command, "").trim();
	    EventSetUp esu = new EventSetUp();
	    TimerTriggers tt = new TimerTriggers();
		
		System.out.println(command);
		
		if(!(args.isEmpty())) {
			System.out.println(String.format("%s: %s", e.getAuthor().getName(), " " + args));
			e.getChannel().sendMessage(String.format("%s: %s", e.getAuthor().getAsMention(), " " + args)).queue();
		}
		
		//gives information
		if(command.equalsIgnoreCase("?") || command.equalsIgnoreCase("help")) {
			information(e);
		}
		
		if(command.equalsIgnoreCase("Event")) {
			if(e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				String[] eventInput = args.split(",");
				esu.repeatCheck(eventInput, e);
			} else {
				return;
			}
		}
		
		//secret......
		if(command.equalsIgnoreCase("Secret123")) {
			ha(e);
		}

		//adds user to list for dms
		if(command.equalsIgnoreCase("Add")) {
			tt.membListAdd(e.getMember());
		}
		
		//removes user from list for dms
		if(command.equalsIgnoreCase("Remove")) {
			tt.membListremove(e.getMember());
		}
		
	}
	
	//output for help
	private void information(MessageReceivedEvent e) {
		e.getChannel().sendMessage(String.format("The available list of commands are: \n\nGeneral Commands\n\nHelp:\nThese massages\n\nSet: \nSet is used to set an event reminder for a party.\n "
				+ "It can only be used by certain individuals within P1.\n To set the event enter in like this: \nMM/DD/YYYY 16:00\n Military time is required and the "
				+ "time zone difference between your time zone and the GTC must be accounted for.\n An example of this is adding 5 hours to the time for EST.\n\nAdd: \n"
				+ "add yourself to the events reminder list.\n\nRemove:\n remove yourself from the reminder list.")).queue();
	}
	
	//set event uses SE 8's new Instant class
	
	
	private void ha(MessageReceivedEvent e) {
		e.getChannel().sendMessage("<@133031662735982592> IS 100% unequivocally undeniably a SEAGULL!!!!!!!!!!").queue();
		try {
			e.getMessage().delete().queue();
		} catch(InsufficientPermissionException ipe) {
			System.out.println("The bot doesn't have the required permissions!!!!");
		}
	}
	
}






//new TimerTriggers().onTimerTriggers(eventMills, adds);
//membs = e.getGuild().getDefaultChannel().getMembers();