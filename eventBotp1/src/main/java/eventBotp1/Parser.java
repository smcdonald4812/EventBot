package eventBotp1;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Parser extends ListenerAdapter{
	
	public static String parsArg(String args) {
		//get rid of common date symbols except for colons (for Instant.parse())
		if(args.contains("/")) {
			args = args.replace("/", " ");
		}
		if(args.contains("-")) {
			args = args.replace("-", " ");
		}
		//split into an array for proper checking takes M D Y and turns it into Y M D
		String[] x = args.split(" ");
		String a = x[0];
		String b = x[1];
		String c = x[2];
		//instant must have YYYY-MM-DDTHH:MM:SSS
		if(c.length() == 2) {
			c = "20" + c;
		}
		if(b.length() != 2) {
			b = "0" + b;
		}
		if(a.length() != 2) {
			a = "0" + a;
		}
		//proper formatting for instant parsing (SE 8's new date time is a pain in the ass)
		args = "" + c + "-" + a + "-" + b + "T" + x[3] + ":00Z";
		return args;
	}
}
