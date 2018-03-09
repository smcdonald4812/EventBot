package eventBotp1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class eventBot extends ListenerAdapter {

	static String prefix;
	public static List<Member> adds = new ArrayList<Member>();
	
	public static void main(String[] args) {

		// gets need information from your config.txt file much like getting it form the
		// JSON file for discord.js
		List<String> config = null;
		try {
			config = Files.readAllLines(Paths.get("config.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String token = config.get(0);
		prefix = config.get(1);

		// create and set bot
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setGame(Game.listening("with JDA"));
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setToken(token);
		JDA jda = null;
		try {
			jda = builder.buildAsync();
		} catch (LoginException ex) {
			ex.printStackTrace();
		}

		// events
		jda.addEventListener(new ReadyListener());
		jda.addEventListener(new MessageReceivedListener());
	}
}
