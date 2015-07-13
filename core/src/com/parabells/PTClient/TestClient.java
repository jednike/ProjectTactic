package com.parabells.PTClient;
import java.io.*;
import java.net.*;
	
class BufferListener implements Runnable{
	BufferedReader in;
	boolean wantClose = false;
	
	public BufferListener(BufferedReader br){
		this.in = br;
	}
	
	public void Close(){
		wantClose = true;
		System.out.println("in == null");
	}

	@Override
	public void run(){
		String input;
		try {
			while (!wantClose && (input = in.readLine()) != null ) {
				if (input.equalsIgnoreCase("exit")) break;
				System.out.println(input);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BufferListener "+Thread.currentThread().getName()+" Closed");
	}
}
	
	
public class TestClient {

	public static void main(String[] args) throws IOException {
		Socket fromserver = null;
		fromserver = new Socket(InetAddress.getLocalHost(),7070);

		Thread threadListener;
		
		BufferedReader in  = new BufferedReader(new	InputStreamReader(fromserver.getInputStream()));
		PrintWriter    out = new PrintWriter(fromserver.getOutputStream(),true);
		BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		BufferListener bl = new BufferListener(in);
		
		threadListener = new Thread(bl,"myBL thread");
		threadListener.setDaemon(true);
		threadListener.start();
		
		String fuser;
		while ((fuser = inu.readLine())!=null) {
			out.println(fuser);
			if (fuser.equalsIgnoreCase("close")) break;
			if (fuser.equalsIgnoreCase("exit")) break;
		}
		bl.Close();
		out.close();
		
		in.close();
		inu.close();
		
		fromserver.close();
		System.out.println("ClientClosed");
	}
}
