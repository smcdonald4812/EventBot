package eventBotp1;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
	
	public void onReady(ReadyEvent e) {
		System.out.println(String.format("[%s#%s] I'm online!!!", e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getDiscriminator()));
	}
}
