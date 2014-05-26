package kusix.myflowers.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import kusix.myflowers.protocol.ProtocolParser;
import kusix.myflowers.protocol.impl.JsonProtocolParser;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothClient {

	protected static final int CONNECT_FAILED = 1;
	protected static final int CONNECT_SUCCESS = 0;
	protected static final int READ_FAILED = 2;
	protected static final int WRITE_FAILED = 3;
	protected static final int DATA = 4;

	private BluetoothDevice device;
	private Handler handler;
	private BluetoothSocket socket;
	private boolean isConnect;

	public BluetoothClient(BluetoothDevice device, Handler handler) {
		this.device = device;
		this.handler = handler;
	}

	public void connect(final String message) {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				BluetoothSocket tmp = null;
				Method method;
				try {
					method = device.getClass().getMethod("createRfcommSocket",
							new Class[] { int.class });
					tmp = (BluetoothSocket) method.invoke(device, 1);

					if (null != tmp) {
						socket = tmp;
						socket.connect();
						isConnect = true;
						setState(CONNECT_SUCCESS);
						if (isConnect) {
							write(message);
							read(new JsonProtocolParser());
						}

						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e) {
								Log.e(getClass().getSimpleName(), e.toString());
								socket = null;
							}
						}
					}
				} catch (Exception e) {
					setState(CONNECT_FAILED);
					Log.e(getClass().getSimpleName(), e.toString());
				}
			}

			private void read(ProtocolParser parser) {
				try {
					InputStream inputStream = socket.getInputStream();
					int bufferSize = 1024;
//					char[] buffer = new char[bufferSize];
//					int i = 0;
//					do {
//						try {
//							buffer[i] = (char)inputStream.read();
//						} catch (IOException e) {
//							setState(READ_FAILED);
//							Log.e(getClass().getSimpleName(), e.toString());
//							break;
//						}
//					} while (buffer[i++] != parser.getEOF() && i < bufferSize);
					byte[] bytes = readStream(inputStream);
					 Message msg = handler.obtainMessage();
					 msg.what = DATA;
					 msg.obj = parser.parse(new String(bytes));
					 handler.sendMessage(msg);
					
				} catch (IOException e) {
					setState(WRITE_FAILED);
					Log.e(getClass().getSimpleName(), e.toString());
				}
			}
			
			public byte[] readStream(InputStream inputStream) throws IOException {  
		        ByteArrayOutputStream bout = new ByteArrayOutputStream();  
		        byte[] buffer = new byte[1024];  
		        int len = 0;  
		        while ((len = inputStream.read(buffer)) != -1) {  
		            bout.write(buffer, 0, len);  
		        }  
		        bout.close();  
		        inputStream.close();  
		  
		        return bout.toByteArray();  
		    }

			private void write(final String message) {
				if (null != message && message.length() > 0) {
					try {
						OutputStream outStream = socket.getOutputStream();
						outStream.write(getHexBytes(message));
					} catch (IOException e) {
						setState(WRITE_FAILED);
						Log.e(getClass().getSimpleName(), e.toString());
					}
				}
			}

			private void setState(int state) {
				handler.sendMessage(handler.obtainMessage(state));
			}
		});
		thread.start();
	}

	private byte[] getHexBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return bytes;
	}

}
