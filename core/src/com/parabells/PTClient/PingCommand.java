package com.parabells.PTClient;

public class PingCommand extends DefaultCommand {
	int number = 0;
	public PingCommand (int number)
	{
		this.number = number;
		name = "Ping";
	}

}
