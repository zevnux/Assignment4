package patch;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;

import com.google.common.io.BaseEncoding;

public class PatchPassword {
	public static void main(String[] args) {
		try {
			// Ensure path of the program
			System.out.println("Please make sure 32966111.program2.exe is in the same current directory");
			
			// Get the user to input a password
			System.out.println("Choose a new password for student 32966111's program");
			Scanner input = new Scanner(System.in);
			String password = input.nextLine();
			System.out.println("Your password is: " + password);
			
			// Hash that input
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        password = getHex(crypt.digest());
	        System.out.println("Your password hash is: " + password);
	        
	        // Get the original file, and overwrite the password
			Path pathOriginal = Paths.get("32966111.program2.exe");
			Path pathNew = Paths.get("32966111.program2new.exe"); // use this if you want new file
			byte[] data = Files.readAllBytes(pathOriginal);
			String s = new String(getHex(data));
			// Replace the password here
			s = s.replaceAll("C0927E6D80938E6CC965BE000D691D517726B27B", password);
			//System.out.println("File content: " + s);
			
			// Put it back to bytes and write a new file/ overwrite the file
			byte[] b = BaseEncoding.base16().decode(s);
			Files.write(pathNew, b); 
			//Files.write(pathOriginal, b); // use this if you want to overwrite file		
			input.close();
			
			System.out.println("Created new file 32966111.program2new.exe with your password");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Found getHex online to change bytes to string hex
	public static String getHex( byte [] raw ) {
		String HEXES = "0123456789ABCDEF";
		if ( raw == null ) {
			return null;
		}
		final StringBuilder hex = new StringBuilder( 2 * raw.length );
		for ( final byte b : raw ) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4))
			.append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}