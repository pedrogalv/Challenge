package com.gamesys.challenge;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class GamesyschallengeApplicationTests {

    @Autowired
    private Shell shell;

    @Test
    public void helpCommandShouldDisplayCustomOperators() {
        Object help = shell.evaluate(() -> "help");

        assertThat(help.toString(), Matchers.containsString("list: List all books stored."));
        assertThat(help.toString(), Matchers.containsString("price: Check price of book ids."));
    }

    @Test
    public void helpPriceShouldExplainInput() {
        Object help = shell.evaluate(() -> "help price");

        assertThat(help.toString(), Matchers.containsString("List of all books ids desired to check price. Example: 2,5,11"));
    }

    @Test
    public void listCommandShouldDisplayBooksWithId() {
        Object help = shell.evaluate(() -> "list");

        assertThat(help.toString(), Matchers.containsString("[1](title='Moby Dick', year=1851, price=15.2)"));
        assertThat(help.toString(), Matchers.containsString("[12](title='Harry Potter and the Goblet of Fire', year=2000, price=3.85)"));
    }

    @Test
    public void priceCommandShouldDisplayPriceFormatted() {
        Object help = shell.evaluate(() -> "price 2,5,11,12");

        assertThat(help.toString(), Matchers.containsString("£39.30"));
    }
}
