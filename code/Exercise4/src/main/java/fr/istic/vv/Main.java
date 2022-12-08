package fr.istic.vv;

import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.err.println("Should provide the path to the source code");
            System.exit(1);
        }

        File file = new File(args[0]);
        if(!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);
        }

        SourceRoot root = new SourceRoot(file.toPath());
        PublicElementsPrinter printer = new PublicElementsPrinter();
        root.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(printer, null));
            return SourceRoot.Callback.Result.DONT_SAVE;
        });


        //create a new rapport with the name of the class and package for a field who don't have public getter
        File rapport = new File("rapport.txt");
        if (!rapport.exists()) {
          rapport.createNewFile();
        }

        FileWriter fw = new FileWriter(rapport.getAbsoluteFile());
         BufferedWriter bw = new BufferedWriter(fw);
         for(String name : printer.getList()){
           String[] tab=name.split(":");

           if(tab.length==3){
             bw.write("Package: "+tab[0]+"    Class: "+tab[1]+"   Variable:" +tab[2]);
           }else{
             bw.write("Package: default"+"    Class: "+tab[0]+"   Variable:" +tab[1]);
           }
           bw.write("\n");
         }
         //bw.write(content);
         bw.close();
    }


}
