package Token;
public class TestToken {
    public static void main(String[] args) {
		Token t1 = new Token("add");
		Token t2 = new Token("halt");
		Token t3 = new Token("tlt");
		Token t4 = new Token("div");
		
		System.out.print("Test Token\n");
		System.out.print("add " +"halt "+"tlt "+"div \n");
		System.out.print(t1.getTokenName()+" ");
		System.out.print(t2.getTokenName()+" ");
		System.out.print(t3.getTokenName()+" ");
		System.out.print(t4.getTokenName()+" \n");
		System.out.println();


	}
}