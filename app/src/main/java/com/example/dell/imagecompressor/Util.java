package com.example.dell.imagecompressor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Util {

    Context _context;
    final static String preferenceKey = "direc";

    public static void startAnActivity(Context context, Class className, String value) {
        Intent intent = new Intent(context, className);
        intent.putExtra("value", value);
        context.startActivity(intent);
    }

    // constructor
    public Util(Context context) {
        this._context = context;
    }

    /*
     * Reading file paths from SDCard
     */
    public ArrayList<String> getFilePaths(String path) {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = Environment.getExternalStoragePublicDirectory(path);

        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        _context,
                        AppConstant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    public ArrayList<String> getAllFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = Environment.getExternalStorageDirectory();

        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        _context,
                        AppConstant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    /*
     * Check supported file extensions
     *
     * @returns boolean
     */
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    /*
     * getting screen width
     */
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }


//	File[]dirs = f.listFiles();
//	this.setTitle("Current Dir: "+f.getName());
//	List<Item>dir = new ArrayList<Item>();
//	List<Item>fls = new ArrayList<Item>();
//	try{
//		for(File ff: dirs)
//		{
//			Date lastModDate = new Date(ff.lastModified());
//			DateFormat formater = DateFormat.getDateTimeInstance();
//			String date_modify = formater.format(lastModDate);
//			if(ff.isDirectory()){
//
//
//				File[] fbuf = ff.listFiles();
//				int buf = 0;
//				if(fbuf != null){
//					buf = fbuf.length;
//				}
//				else buf = 0;
//				String num_item = String.valueOf(buf);
//				if(buf == 0) num_item = num_item + " item";
//				else num_item = num_item + " items";
//
//				//String formated = lastModDate.toString();
//				dir.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
//			}
//			else
//			{
//				fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"file_icon"));
//			}
//		}








    public String getAFile(String directory) {
        String fileName = "";
        File storageDir = new File(directory);
        if (storageDir.isDirectory()) {
            File[] listFile = storageDir.listFiles();
            if (listFile != null && listFile.length > 0) {
                for (File file : listFile) {
                    if (file.isDirectory()) {

                    } else {
                        if (file.getName().endsWith(".png")
                                || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg")
                                || file.getName().endsWith(".gif")
                                || file.getName().endsWith(".bmp")
                                || file.getName().endsWith(".webp")) {
                            fileName = file.getAbsolutePath();
                        }
                    }
                }
            }
        }
        return fileName;
    }

    public static List<String> getAllDirectories(File filee) {
        List<String> list = new ArrayList<String>();
        File[] fList = filee.listFiles();
        for (File file : fList) {
            if (file.isFile()) {

            } else if (file.isDirectory()) {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public ArrayList<String> getNotNullImageDirectories(String dirr) {
        File dir = new File(dirr);
        ArrayList<String> list = new ArrayList<String>();
        for(String i : getAllDirectories(dir)) {
            if(getAFile(i).equals("")) {

            } else {
                list.add(getAFile(i).substring(0, getAFile(i).lastIndexOf('/')));
            }
        }
        return list;
    }

    public ArrayList<String> getAFileFromAllNotNullDirectories(String dir) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> direc = getNotNullImageDirectories(dir);
        for(int i = 0; i<direc.size(); i++) {
            list.add(getAFile(direc.get(i)));
        }
        return list;
    }

    public ArrayList<String> getAllFilesDirectoryWithNotNullImageDirectories(String directory) {
        ArrayList<String> list = new ArrayList<>();
        File storageDir = new File(directory);
        if (storageDir.isDirectory()) {
            File[] listFile = storageDir.listFiles();
            if (listFile != null && listFile.length > 0) {
                for (File file : listFile) {
                    if (file.isDirectory()) {

                    } else {
                        if (file.getName().endsWith(".png")
                                || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg")
                                || file.getName().endsWith(".gif")
                                || file.getName().endsWith(".bmp")
                                || file.getName().endsWith(".webp")) {
                            list.add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return list;
    }

    /*
	 * Resizing image size
	 */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {

            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getAllFiles(String directory) {
        ArrayList<String> list = new ArrayList<String>();
        File storageDir = new File(directory);
        if (storageDir.isDirectory()) {
            File[] listFile = storageDir.listFiles();
            if (listFile != null && listFile.length > 0) {
                for (File file : listFile) {
                    if (file.isDirectory()) {

                    } else {
                        if (file.getName().endsWith(".png")
                                || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg")
                                || file.getName().endsWith(".gif")
                                || file.getName().endsWith(".bmp")
                                || file.getName().endsWith(".webp")) {
                            list.add(file.getName());
                        }
                    }
                }
            }
        }
        return list;
    }

    static void saveImage(Bitmap finalBitmap, Context context) {
        SessionManager sessionManager = new SessionManager();
        final String preferenceKey = "direc";
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(sessionManager.getPreferences(context, preferenceKey), fname);
        Log.i("fileeeee", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void shortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
