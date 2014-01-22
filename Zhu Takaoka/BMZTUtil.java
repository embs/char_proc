import java.util.ArrayList;
import java.util.HashMap;


public class BMZTUtil {
	static final boolean DEBUG_BAD_CHARACTER_TABLE = true;
	static final boolean DEBUG_GOOD_SUFFIX_TABLE = true;
	static final boolean DEBUG_SUFFIX_TABLE = true;
	static final boolean PRINT_SHIFT = true;
//	static final boolean DEBUG_BAD_CHARACTER_TABLE = false;
//	static final boolean DEBUG_GOOD_SUFFIX_TABLE = false;
//	static final boolean DEBUG_SUFFIX_TABLE = false;
//	static final boolean PRINT_SHIFT = false;
	
//	Printa em formato de tabela
	public static void printAsTable(String title, String labels[], HashMap<String, ArrayList<String>> values, int columnSize, int lineAmount, String verticalLabels[]){
		//Gera Strings que representam as linhas horizontais
		char lineChar = '_';
		StringBuffer str = new StringBuffer();
		StringBuffer horizontalLinebuffer = new StringBuffer();
		for(int j=0; j<columnSize+1; j++) 
			horizontalLinebuffer.append(lineChar);
		String horizontalLineTop = horizontalLinebuffer.toString();
		int extraVerticalColumnSize = 0;
		if(verticalLabels != null && verticalLabels.length > 0){
			horizontalLinebuffer.append(horizontalLineTop);
			extraVerticalColumnSize = 1;
		}
		for(int i=1; i<labels.length; i++)
			horizontalLinebuffer.append(horizontalLineTop);
		horizontalLinebuffer.append(lineChar);
		horizontalLineTop = horizontalLinebuffer.toString();
		String horizontalLineBottom = horizontalLineTop.replace(lineChar,'\u00AF');
		
		//Printa a tabela
		String labelFormat = "%" + columnSize + "s|";
		String numberFormat = "%" + columnSize + "s|";
		str.append(horizontalLineTop).append("\n");
		
		str.append(String.format("|%-" + ((columnSize+1)*(labels.length + extraVerticalColumnSize)-1)  +"s|", title)).append("\n|");
		if(verticalLabels != null && verticalLabels.length > 0)
			str.append(String.format(labelFormat, " "));
		for(int i=0; i<labels.length; i++){
			str.append(String.format(labelFormat, labels[i]));
		}
		str.append("\n");
		for(int i=0; i<lineAmount; i++){
			str.append("|");
			if(verticalLabels != null && verticalLabels.length > 0)
				str.append(String.format(numberFormat, verticalLabels[i]));
			for(int j=0; j<labels.length; j++){
				String temp = "";
				try{ temp = values.get(labels[j]).get(i); }
				catch(Exception e){}
				str.append(String.format(numberFormat, temp));
			}
			str.append("\n");
		}
		str.append(horizontalLineBottom);
		System.out.println(str.toString());
	}
	
	public static String[] objectsToSTrings(Object[] array){
		String retorno[] = new String[array.length];
		for(int i=0; i<retorno.length; i++){
			retorno[i] = array[i].toString();
		}
		return retorno;
	}
	
//	Printa em formato de tabela
//	public static void printAsTable(String title, String labels[], HashMap<String, ArrayList<String>> values, int columnSize, int lineAmount){
//		//Gera Strings que representam as linhas horizontais
//		char lineChar = '_';
//		StringBuffer str = new StringBuffer();
//		StringBuffer horizontalLinebuffer = new StringBuffer();
//		for(int j=0; j<columnSize+1; j++) 
//			horizontalLinebuffer.append(lineChar);
//		String horizontalLineTop = horizontalLinebuffer.toString();
//		for(int i=1; i<labels.length; i++)
//			horizontalLinebuffer.append(horizontalLineTop);
//		horizontalLinebuffer.append(lineChar);
//		horizontalLineTop = horizontalLinebuffer.toString();
//		String horizontalLineBottom = horizontalLineTop.replace(lineChar,'\u00AF');
//		
//		//Printa a tabela
//		String labelFormat = "%" + columnSize + "s|";
//		String numberFormat = "%" + columnSize + "s|";
//		str.append(horizontalLineTop).append("\n");
//		str.append(String.format("|%-" + ((columnSize+1)*labels.length-1)  +"s|", title)).append("\n|");
//		for(int i=0; i<labels.length; i++){
//			str.append(String.format(labelFormat, labels[i]));
//		}
//		str.append("\n");
//		for(int i=0; i<lineAmount; i++){
//			str.append("|");
//			for(int j=0; j<labels.length; j++){
//				String temp = "";
//				try{ temp = values.get(labels[j]).get(i); }
//				catch(Exception e){}
//				str.append(String.format(numberFormat, temp));
//			}
//			str.append("\n");
//		}
//		str.append(horizontalLineBottom);
//		System.out.println(str.toString());
//	}
}
