package Utils;

import java.io.*;
import java.util.ArrayList;

public class FileManager implements Serializable{

    private File file;
    private transient  DataOutputStream outputStream;
    private transient  DataInputStream inputStream;
    private transient  ObjectOutputStream outputStreamObject;
    private transient ObjectInputStream inputStreamObject;

    public void CreateFile_ObjectStream(String name) {
        this.file = new File(name);
        try {
            if (!file.exists()) {
                file.createNewFile();
                outputStreamObject = new ObjectOutputStream(new FileOutputStream(file, true));
            } else {
                outputStreamObject = new AppendableObjectOutputStream(new FileOutputStream(file, true));
            }
            inputStreamObject = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void CreateFile_DataStream(String name) {
        this.file = new File(name);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new DataOutputStream(new FileOutputStream(file, true));
            inputStream = new DataInputStream(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void CloseFile() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStreamObject != null) {
                outputStreamObject.close();
            }
            if (inputStreamObject != null) {
                inputStreamObject.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void WriteObject(Object input) {
        try {
            outputStreamObject.writeObject(input);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public Object GetObject() {
        try {
            return inputStreamObject.readObject();
        }catch (IOException | ClassNotFoundException e) {
            if(!(e instanceof EOFException))
                System.out.println("An error occurred");
            return null;
        }
    }

    public void ClearAndWriteObject(Object input) {
        try {
            file.createNewFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(input);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void Writeln(String s) {
        try {
            outputStream.writeBytes(s + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void Write(String s) throws IOException {
        try {
            outputStream.writeBytes(s);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public ArrayList<String> GetLines() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        return lines;
    }

    public void ClearAndWriteLines(ArrayList<String> lines) {
        try {
            file.createNewFile();
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
            for (String line : lines) {
                outputStream.writeBytes(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

}

class AppendableObjectOutputStream extends ObjectOutputStream {

    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // Do nothing. Prevents writing of header.
    }
}
