package swing.sample;

import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





class MyFrame extends JFrame
{
	StyleContext sc = new StyleContext();
	DefaultStyledDocument doc = new DefaultStyledDocument( sc );
	Style s = null;
	kakujoshicheck kc =  new kakujoshicheck();

	public MyFrame()
	{
		final JTextField jtf = new JTextField();
		jtf.setText(Double.toString(kc.getThreshold()));
		JButton jb = new JButton("Reload");
		JLabel thre = new JLabel("Threshold = ");
		thre.setHorizontalAlignment(JLabel.RIGHT);
		
		class ActionAdapter implements ActionListener{
	@Override
			public void actionPerformed(ActionEvent ev) {
				String text = new String(); 
				System.out.println("reloading......");
				try {
					double threshold = Double.valueOf(jtf.getText());
					kc.setThreshold(threshold);
					text = doc.getText(0, doc.getLength());
				} catch (BadLocationException e) 
				{
				}
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e) {
				}
				reload(text);
				showText();
			}
		}

		//JFrame frame = new JFrame("mainframe");

		jb.addActionListener(new ActionAdapter());

		setSize( 800, 500 );

		Container cont = getContentPane();		//コンテント・ペインの取得

		cont.setLayout(new BorderLayout());		//レイアウトマネージャ
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	getContentPane().setLayout( new BorderLayout() );
		JTextPane tp = new JTextPane(doc);

		JPanel controller = new JPanel(new GridLayout(1, 2));
		JPanel textarea = new JPanel(new BorderLayout());
		JPanel thresetter = new JPanel(new GridLayout(1, 2));
		
		controller.add(jb);
		thresetter.add(thre);
		thresetter.add(jtf);
		
		controller.add(thresetter);
		textarea.add(tp, BorderLayout.CENTER);
		

		//コンテントペインに部品を配置
		cont.add(controller ,BorderLayout.NORTH);
		cont.add(textarea ,BorderLayout.CENTER);

		//getContentPane().add( p, "Center" );

		s = sc.addStyle("normal", null);
		StyleConstants.setFontFamily( s, "Dialog" );
		StyleConstants.setFontSize( s, 20 );
		StyleConstants.setBold( s, false );
		StyleConstants.setItalic( s, false );
		StyleConstants.setUnderline( s, false );
		StyleConstants.setStrikeThrough( s, false );

		s = sc.addStyle("underlined", null);
		StyleConstants.setFontFamily( s, "Dialo" );
		StyleConstants.setFontSize( s, 20 );
		StyleConstants.setBold( s, false );
		StyleConstants.setItalic( s, false );
		StyleConstants.setUnderline( s, true );
		StyleConstants.setStrikeThrough( s, false );
		StyleConstants.setForeground(s, Color.red);
	    //frame.show();

	}
	public void setThreshold(double t){
		kc.setThreshold(t);
	}
	public void reload(String text){
		try{
			ArrayList<String> l = new ArrayList<String>();
			
			
			
			
			l = kc.Reload(text);
			Iterator<String> kc_it = l.iterator();
			while(kc_it.hasNext()){
				String word = kc_it.next();
				String[] word_check = word.split(",");
				if(Integer.valueOf(word_check[1])==1){
					s = sc.getStyle("underlined");
					doc.insertString(doc.getLength(),word_check[0],s);
					s = sc.getStyle("normal");
				}
				else{
					doc.insertString(doc.getLength(),word_check[0],s);
				}
			}

		}catch (Exception e)
		{
		}
	}

	public void getThreashold(){
		
	}
	public void setText(String text){
		try{
			s = sc.getStyle("normal");
			ArrayList<String> l = new ArrayList<String>();
			l = kc.check(text);
			Iterator<String> kc_it = l.iterator();
			while(kc_it.hasNext()){
				String word = kc_it.next();
				String[] word_check = word.split(",");
				if(Integer.valueOf(word_check[1])==1){
					s = sc.getStyle("underlined");
					doc.insertString(doc.getLength(),word_check[0],s);
					s = sc.getStyle("normal");
				}
				else{
					doc.insertString(doc.getLength(),word_check[0],s);
				}
			}

		}catch (Exception e)
		{
			System.out.println("Exception:"+e.toString());
		}
	}
	public void showText(){
		show();
	}


	public static void main(String s[])
	{
		System.out.println("------------start--------------");

		String text = "ご存知の方もいらっしゃるかと思いますがががががががががが、著者をがノンフィクションライターで、サッカーが中田選手、イチロー選手、歌舞伎の中村勘三郎さん、歌手はYOSHIKIさんといった方々に行ったインタビューをまとめた本をこれまでに出版していらっしゃいます。私も中田選手やイチロー選手の本は読んだことがあって、「インタビューを受ける機会が少ない中田選手やイチロー選手にインタビューを行なって、それを本にするなんて、この小松さんという女性はいったいどういう人なんだろう」って以前から興味があったんです。ですので、店頭にこの本が並んでいるのを見つけて、すぐに購入して一気に読みましたこの本を読んで初めて知ったのですが、小松さんは、20歳で広告代理店に就職したものの、3年で辞め、その後自分探しの空白期間があり、27歳の時にライターになることを志したのだそうです。もちろん、ライターとしての経験もなく、ゼロからのスタートです。";//tp.getText();
		MyFrame frame = new MyFrame();
		frame.setText(text);
		frame.showText();
		System.out.println("------------done--------------");

	}
}