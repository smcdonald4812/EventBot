/*
	want to eventually use timer.scheduleAtFixedRate(TimerTask, Date, long) to make the events fire on a repeated basis
*/
package eventBotp1;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimerTriggers {
	
	// dms memb list
	private List<Member> adds = eventBot.adds;
	private Timer timer = new Timer();
	private final Long threeDay = 259200000L, twoDay = 172800000L, oneDay = 86400000L, sixHour = 21600000L,
			fourHour = 14400000L, twoHour = 7200000L, oneHour = 3600000L, fifteenMin = 900000L;

	public void membListAdd(Member a) {
		adds.add(a);
		System.out.println(adds);
	}

	public void membListremove(Member a) {
		adds.remove(a);
		System.out.println(adds);
	}

	@SuppressWarnings("unused")
	public void setTriggers(String[] s, MessageReceivedEvent msg) {
		String repeats = s[0].trim(), eventName = s[1].trim(), eventDate = s[2].trim();
		Long dateMills = 0L, dmFirstMills = 0L, dmSecondMills = 0L, dmThirdMills = 0L, chatFirstMills = 0L,
				chatSecondMills = 0L, chatThirdMills = 0L;
		switch (repeats) {
		case "yearly":
			dmFirstMills = getEvent(eventDate, threeDay);
			dmSecondMills = getEvent(eventDate, oneDay);
			dmThirdMills = getEvent(eventDate, sixHour);
			chatFirstMills = getEvent(eventDate, twoHour);
			chatSecondMills = getEvent(eventDate, oneHour);
			chatThirdMills = getEvent(eventDate, fifteenMin);
			onTimerTriggers(dmFirstMills, threeDay, eventName);
			onTimerTriggers(dmSecondMills, oneDay, eventName);
			onTimerTriggers(dmThirdMills, sixHour, eventName);
			onTimerTriggersGeneral(chatFirstMills, twoHour, eventName, msg);
			onTimerTriggersGeneral(chatSecondMills, oneHour, eventName, msg);
			onTimerTriggersGeneral(chatThirdMills, fifteenMin, eventName, msg);
			Event(msg, eventName, eventDate);
			break;
		case "monthly":
			dmFirstMills = getEvent(eventDate, twoDay);
			dmSecondMills = getEvent(eventDate, sixHour);
			chatFirstMills = getEvent(eventDate, oneHour);
			chatSecondMills = getEvent(eventDate, fifteenMin);
			onTimerTriggers(dmFirstMills, twoDay, eventName);
			onTimerTriggers(dmSecondMills, sixHour, eventName);
			onTimerTriggersGeneral(chatFirstMills, oneHour, eventName, msg);
			onTimerTriggersGeneral(chatSecondMills, fifteenMin, eventName, msg);
			Event(msg, eventName, eventDate);
			break;
		case "weekly":
			dmFirstMills = getEvent(eventDate, oneDay);
			dmSecondMills = getEvent(eventDate, fourHour);
			chatFirstMills = getEvent(eventDate, oneHour);
			chatSecondMills = getEvent(eventDate, fifteenMin);
			onTimerTriggers(dmFirstMills, oneDay, eventName);
			onTimerTriggers(dmSecondMills, fourHour, eventName);
			onTimerTriggersGeneral(chatFirstMills, oneHour, eventName, msg);
			onTimerTriggersGeneral(chatSecondMills, fifteenMin, eventName, msg);
			Event(msg, eventName, eventDate);
			break;
		case "daily":
			dmFirstMills = getEvent(eventDate, twoHour);
			chatFirstMills = getEvent(eventDate, fifteenMin);
			onTimerTriggers(dmFirstMills, twoHour, eventName);
			onTimerTriggersGeneral(chatFirstMills, fifteenMin, eventName, msg);
			Event(msg, eventName, eventDate);
			break;
		case "once":
			dmFirstMills = getEvent(eventDate, twoHour);
			chatFirstMills = getEvent(eventDate, fifteenMin);
			onTimerTriggers(dmFirstMills, twoHour, eventName);
			onTimerTriggersGeneral(chatFirstMills, fifteenMin, eventName, msg);
			Event(msg, eventName, eventDate);
			break;
		case "never":
			Event(msg, eventName, eventDate);
		}
	}


	public void Event(MessageReceivedEvent event, String e, String f) {
		event.getChannel().sendMessage(":fireworks: EVENT :fireworks:\n" + e + " is happening at:\n" + f
				+ "\n+add to join the notificaiton list!").queue();
	}

	private Long getEvent(String d, Long mills) {
		long e = 0L;
		d = Parser.parsArg(d);
		Instant event = Instant.parse(d);
		e = event.toEpochMilli() - mills;
		return e;
	}

	public void onTimerTriggers(Long h, Long timeMills, String event) {
		// gets mills and guild list and sends them a message
		Long timed = h - Instant.now().toEpochMilli();
		String s = "minutes";
		Long tMills = timeMills / 1000 / 60;
		if (tMills > 59) {
			tMills = tMills / 60;
			s = "hours";
		}
		if (tMills > 23) {
			tMills = tMills / 24;
			s = "days";
		}

		// closing scope effectively final
		String w = s;
		String timeLeft = String.valueOf(tMills);

		// ensure timed can't be negative
		if (timed < 0L) {
			timed = 0L;
		}
		// scheduled by difference of mills between now and event
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// iterator to get each member from list to dm them message arrow is for Java 8
				// lambda
				for (int i = 0; i < adds.size(); i++) {
					//stop discord from blocking messages
					if (i % 3 == 0) {
						try {
							Thread.sleep(1001);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
					}
					if (!adds.get(i).getUser().isBot()) {
						adds.get(i).getUser().openPrivateChannel().queue((channel) -> {
							channel.sendMessage("Party starts in " + timeLeft + " " + w + "!!!").queue();
						});
					}
				}
			}
		}, timed);
	}

	public void onTimerTriggersGeneral(Long h, Long timeMills, String event, MessageReceivedEvent events) {
		// gets mills and guild list and sends them a message
		Long timed = h - Instant.now().toEpochMilli();
		String s = "minutes";
		Long tMills = timeMills / 1000 / 60;
		if (tMills > 59) {
			tMills = tMills / 60;
			s = "hours";
		}
		if (tMills > 23) {
			tMills = tMills / 24;
			s = "days";
		}

		// closing scope effectively final
		String w = s;
		String timeLeft = String.valueOf(tMills);

		// ensure timed can't be negative
		if (timed < 0L) {
			timed = 0L;
		}

		// scheduled by difference of mills between now and event
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				events.getChannel().sendMessage("Party starts in " + timeLeft + " " + w + "!!!").queue();
			}
		}, timed);
	}

}