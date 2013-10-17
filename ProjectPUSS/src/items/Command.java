package items;

/**
 * Innehåller de olika kommandon, se Figur 1, en användare kan utföra på en
 * tidrapport. Skickas in i och används av HTMLWriter när tidrapporter ska visas
 * för att veta vad som ska visas och om man ska kunna ändra eller inte.
 */
public enum Command {
	delete, show, update, create, sign
}
