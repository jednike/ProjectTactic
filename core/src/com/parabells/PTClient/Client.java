package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{

    private String fuser;
    private Socket fromserver;
    private PrintWriter out;
    private PTGame game;
    private String IPAdress = "31.148.52.191";

    public Client(PTGame game){
        this.game = game;
    }

    public String getFuser() {
        return fuser;
    }

    public void setFuser(String fuser) {
        this.fuser = fuser;
    }

    private void init() throws IOException{
        fuser = "";
        fromserver = new Socket(InetAddress.getLocalHost(), 7070);

        BufferedReader in  = new BufferedReader(new	InputStreamReader(fromserver.getInputStream()));
        out = new PrintWriter(fromserver.getOutputStream(),true);

        fuser = "";
        try {
            while ((fuser = in.readLine()) != null ) {
                if (fuser.equalsIgnoreCase("exit")) break;
                game.addToInputQueue(fuser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("BufferListener " + Thread.currentThread().getName() + " Closed");

        out.close();
        in.close();

        fromserver.close();
        System.out.println("ClientClosed");
    }

    public PrintWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
