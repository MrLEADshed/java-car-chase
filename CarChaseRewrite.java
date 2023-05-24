//何かから車で逃げ続けるローグライクゲーム_release 3.0
import java.io.IOException;
import java.util.Scanner;

public class CarChaseRewrite
{
	public static void main(String[] args) throws IOException
	{
		Game myGame = new Game();
		System.out.println("(GAME STARTED)");
		for (;;)
		{
			myGame.stats();
			myGame.travel();
		}
	}
}
class Game
{
	private int			consumption	= 0;
	private int			gas			= 8500;
	private int			tank		= 10000;
	private int			refill		= 5000;
	
	private int			cost		= 500;
	private int			money		= 1000;
	private int			rate		= randomize(cost);
	
	private int			travel		= 500;
	private int			distance	= 0;
	
	private Scanner		sc			= new Scanner(System.in);
	private String[]	road		= new String[]{"",
			"...The road was smooth...", "...The road was fine...",
			"...The road was not bad...", "...The road was unpleasant...",
			"...The road was horrid...",
			"...How TF did you even get this message..?"};
	
	int randomize(int value)
	{
		return (value + (int) (Math.random() * value)) / 2;
	}
	void flush()
	{
		System.out.print("(PRESS ENTER TO CONTINUE)");
		sc.nextLine();
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	void stats()
	{
		flush();
		
		double	d	= distance;
		double	g	= gas;
		double	m	= money;
		
		d	= d / 10;
		g	= g / 100;
		m	= m / 10;
		
		System.out.println();
		System.out.print("distance:	");
		System.out.println(d + "km");
		System.out.print("gas:		");
		System.out.println(g + "%");
		System.out.print("money:		");
		System.out.println(m + "$");
	}
	void travel()
	{
		int		length;
		double	t;
		
		length	= randomize(travel);
		t		= length;
		
		t		= t / 10;
		for (;;)
		{
			if (check(t) == 0)
			{
				break;
			} else
			{
				refill(rate);
				stats();
			}
		}
		exhaust(length);
		stats();
		
		rate = randomize(cost);
		refill(rate);
	}
	int check(double value)
	{
		System.out.println("\n" + "travel	" + value + "km? (Y/n)");
		String input = sc.nextLine() + 'Y';
		switch (input.charAt(0))
		{
			case 'n' :
			case 'N' :
				System.out.println("You did not travel...");
				return 1;
			default :
				System.out.println("You did travel...");
				return 0;
		}
	}
	void exhaust(int value)
	{
		consumption	= (int) (Math.random() * 4 + 1);
		
		distance	= distance + value;
		gas			= gas - (consumption * value);
		
		if (gas < 0)
		{
			distance = distance + (gas / consumption);
			System.out.println("...But you could not...");
			end();
		}
		System.out.println(road[consumption]);
	}
	void refill(int value)
	{
		double	v;
		double	r;
		
		v	= value;
		r	= refill;
		
		v	= v / 10;
		r	= r / 100;
		
		System.out.println();
		System.out.print("Refill ");
		System.out.print((int) (r));
		System.out.print("% for ");
		System.out.print(v);
		System.out.println("$? (y/N)");
		
		String input = sc.nextLine() + 'N';
		
		switch (input.charAt(0))
		{
			case 'y' :
			case 'Y' :
				System.out.println("You did refill...");
				if (money == 0)
				{
					System.out.println("...But you could not...");
				} else if (money < cost)
				{
					System.out.println("...But you could not quite do so...");
					double	m;
					double	p;
					
					m		= money;
					r		= refill;
					v		= value;
					
					p		= m / v;
					r		= (r * p);
					
					refill	= (int) (r);
					gas		= gas + refill;
					money	= 0;
				} else
				{
					money	= money - value;
					gas		= gas + refill;
				}
				gas = (gas > tank) ? tank : gas;
			break;
			default :
				System.out.println("You did not refill...");
			break;
		}
	}
	void end()
	{
		sc.close();
		double d = distance;
		
		d = d / 10;
		
		System.out.println();
		System.out.println("One more goner in the world...");
		System.out.print("Distance traveled: ");
		System.out.print(d);
		System.out.print("km");
		System.exit(0);
	}
}