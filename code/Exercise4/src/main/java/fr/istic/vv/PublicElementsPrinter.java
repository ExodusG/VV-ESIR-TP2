package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

// This class visits a compilation unit and
// they store all private attribute don't have public getter
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {

    private ArrayList<String> listeName=new ArrayList<String>();
    private String packages="";
    private String name="";

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
        if(unit.getPackageDeclaration().isPresent()){
          PackageDeclaration n =unit.getPackageDeclaration().get();
          this.packages=n.getName().asString(); // to store the package
        }else{
          this.packages="";
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if(!declaration.isPublic()) return;

        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg); //accept all method for have the name
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        this.name=declaration.getName().asString();
        for(FieldDeclaration n:declaration.getFields()){
          n.accept(this,arg);
        }
        visitTypeDeclaration(declaration, arg);
    }

    //get the name of method and see if they match the patern get+AttributName
    //if this match the attribute was removed of the list
    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;

        String nameMethod=declaration.getName().asString();
        Iterator<String> it=listeName.iterator();
        while(it.hasNext()){
          String nameVar=it.next();
          String[] tab=nameVar.split(":");
          if(tab.length==3){
            if(("get"+tab[2]).equalsIgnoreCase(nameMethod)){
              it.remove();
            }
          }else{
            if(("get"+tab[1]).equalsIgnoreCase(nameMethod)){
              System.out.println("remove:"+tab[1]);
              it.remove();
            }
          }
        }
    }

    //visit the field declaration of the class
    @Override
    public void visit(FieldDeclaration n,Void arg){
      if(!n.isPrivate()) return;
        for(VariableDeclarator var : n.getVariables()){
            var.accept(this,arg);
        }
    }

    //visit the variable declaration of the field of the class and add to the list with package and class name
    @Override
    public void visit(VariableDeclarator n,Void arg) {
      String nameVar=(n.getName().asString());
      if(this.packages!=""){
        listeName.add(this.packages+":"+this.name+":"+nameVar);
      }else{
          listeName.add(this.name+":"+nameVar);
      }
    }

    public ArrayList<String> getList(){
      return this.listeName;
    }
}
