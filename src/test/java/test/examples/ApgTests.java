package test.examples;

import examples.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ssill2
 */
public class ApgTests
{

    public ApgTests()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
    }

    @AfterAll
    public static void tearDownClass()
    {
    }

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void runDemoAstTest()
    {
        String[] args = new String[]
        {
            "/test=demo-ast"
        };

        Main.main(args);
    }
    
    @Test
    public void runDemoAstCallbackTest()
    {
        String[] args = new String[]
        {
            "/test=demo-ast-callback"
        };

        Main.main(args);
    }

    @Test
    public void runDemoUdtAstTest()
    {
        String[] args = new String[]
        {
            "/test=demo-udt-ast"
        };

        Main.main(args);
    }

    @Test
    public void runDemoUdtAltTest()
    {
        String[] args = new String[]
        {
            "/test=demo-udt-alt"
        };

        Main.main(args);
    }

    @Test
    public void runDemoTraceTest()
    {
        String[] args = new String[]
        {
            "/test=demo-trace"
        };

        Main.main(args);
    }

    @Test
    public void runAnbnTest()
    {
        String[] args = new String[]
        {
            "/test=anbn"
        };

        Main.main(args);
    }

    @Test
    public void runAnbncnTest()
    {
        String[] args = new String[]
        {
            "/test=anbncn"
        };

        Main.main(args);
    }

    @Test
    public void runExpressionsTest()
    {
        String[] args = new String[]
        {
            "/test=expressions"
        };

        Main.main(args);
    }

    @Test
    public void runInifileTest()
    {
        String[] args = new String[]
        {
            "/test=inifile"
        };

        Main.main(args);
    }

    @Test
    public void runMailboxTest()
    {
        String[] args = new String[]
        {
            "/test=mailbox"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibAlphanumTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-alphanum"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibAnyTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-any"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibCommentTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-comment"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibCommentSemiTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-comment-semi"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibCommentCppTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-comment-cpp"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibCommentCTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-comment-c"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibDecNumTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-decnum"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibHexNumTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-hexnum"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibLineEndTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-lineend"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibLineEndForgivingTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-lineend-forgiving"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibLineEndLFTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-lineend-lf"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibLineEndCRLFTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-lineend-crlf"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibQuotedStringTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-quoted-string"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibWspTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-wsp"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibWspCommentsTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-wsp-comments"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibWspFoldingTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-wsp-folding"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibWspCFTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-wsp-comments-folding"
        };

        Main.main(args);
    }

    @Test
    public void runUdtlibWspFCTest()
    {
        String[] args = new String[]
        {
            "/test=udtlib-wsp-folding-comments"
        };

        Main.main(args);
    }
}
