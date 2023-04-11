import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @ParameterizedTest(name="{0} sekunnista tulee ajaksi: {1}")
    @CsvSource({ "915, 0:15:15" })
    void testaa_tuleeOikeaTulos(int aika, String oletettuTulos) {
        String laskettuTulos = TimeUtils.secToTime(aika);
        String testiEiOnnistunut = "Aika " + aika + " on laskettu väärin.";

        assertEquals(laskettuTulos, oletettuTulos, testiEiOnnistunut);
    }

    @ParameterizedTest(name="{0} sekunnista tulee ajaksi: {1}")
    @CsvSource({ "0, 0:00:00" })
    void testaa_nollaPalauttaaNollaAjan(int aika, String oletettuTulos) {
        String laskettuTulos = TimeUtils.secToTime(aika);
        String testiEiOnnistunut = "Aika " + aika + " on laskettu väärin.";

        assertEquals(laskettuTulos, oletettuTulos, testiEiOnnistunut);

    }
    @ParameterizedTest(name="{0} sekunnista heitetään virhe negatiivisesta numerosta")
    @ValueSource(ints= {-100, -1})
    void testaa_negatiivinenArvoPalauttaaVirheen(int aika) {
        String testiEiOnnistunut = "Aika " + aika + " ei aiheuta virhettä";
        assertThrows(IllegalArgumentException.class,
                () -> TimeUtils.secToTime(aika), testiEiOnnistunut);
    }

    @ParameterizedTest(name="{0} sekunnista heitetään virhe suuresta numerosta")
    @ValueSource(ints= {100000000, 32000})
    void testaa_suuriArvoPalauttaaVirheen(int aika) {
        String testiEiOnnistunut = "Aika " + aika + " ei aiheuta virhettä";
        assertThrows(IllegalArgumentException.class,
                () -> TimeUtils.secToTime(aika), testiEiOnnistunut);
    }

    @ParameterizedTest(name="{0} sekunnista heitetään virhe kirjaimista")
    @ValueSource(strings= {"JaskaJokunen", "testitapaukset"})
    void testaa_kirjaimetPalauttaaVirheen(String aika) {
        String testiEiOnnistunut = "Aika " + aika + " ei aiheuta virhettä";
        assertThrows(NumberFormatException.class,
                () -> TimeUtils.secToTime(Integer.parseInt(aika)), testiEiOnnistunut);
    }
    @ParameterizedTest(name="{0} sekunnista heitetään virhe erikoismerkeistä")
    @ValueSource(strings= {"&?)", "!+*"})
    void testaa_erikoismerkitPalauttaaVirheen(String aika) {
        String testiEiOnnistunut = "Aika " + aika + " ei aiheuta virhettä";
        assertThrows(NumberFormatException.class,
                () -> TimeUtils.secToTime(Integer.parseInt(aika)), testiEiOnnistunut);
    }

    @ParameterizedTest(name="{0} sekunnista heitetään virhe välilyönneistä")
    @ValueSource(strings= {"5 9 22", "3 3 1"})
    void testaa_valilyonnitPalauttaaVirheen(String aika) {
        String testiEiOnnistunut = "Aika " + aika + " on laskettu väärin.";
        assertThrows(NumberFormatException.class,
                () -> TimeUtils.secToTime(Integer.parseInt(aika)), testiEiOnnistunut);

    }
    @ParameterizedTest(name="Tyhjä parametri heitettää virheen")
    @ValueSource(strings= { "", " "})
    void testaa_tyhjaParametriPalauttaaVirheen(String aika) {
        String testiEiOnnistunut = "Aika " + aika + " on laskettu väärin.";
        assertThrows(NumberFormatException.class,
                () -> TimeUtils.secToTime(Integer.parseInt(aika)), testiEiOnnistunut);

    }

    @ParameterizedTest(name="Tyhjä parametri {0} heitettää virheen")
    @NullSource
    void testaa_nullPalauttaaVirheen(String aika) {
        String testiEiOnnistunut = "Aika " + aika + " on laskettu väärin.";
        assertThrows(NumberFormatException.class,
                () -> TimeUtils.secToTime(Integer.parseInt(aika)), testiEiOnnistunut);

    }
    @ParameterizedTest(name="Palautettu tulos: {1} on merkkijono.")
    @CsvSource({ "29763, 8:16:03" })
    void testaa_metodiPalauttaaMerkkijonon(int aika, String oletettuTulos) {
        String laskettuTulos = TimeUtils.secToTime(aika);
        String testiEiOnnistunut = "Tulos: " + laskettuTulos + " ei ole merkkijono.";

        assertInstanceOf(oletettuTulos.getClass(), laskettuTulos, testiEiOnnistunut);
    }

    @ParameterizedTest(name="Palautettu tulos: {1} on merkkijono oikeassa muodossa.")
    @CsvSource({ "13665" })
    void testaa_metodiPalauttaaMerkkijononOikeassaMuodossa(int aika) {
        String laskettuTulos = TimeUtils.secToTime(aika);
        String testiEiOnnistunut = "Tulos: " + laskettuTulos + " ei ole xx:xx:xx tai x:xx:xx muodossa.";

        char puolipiste =  ':';
        char eka = laskettuTulos.charAt(1);
        char toka = laskettuTulos.charAt(2);
        char neljas = laskettuTulos.charAt(4);
        char viides = laskettuTulos.charAt(5);

        //jos 1 ja 4 on : ,niin oikea muoto
        boolean oikeaMuoto = (eka == puolipiste && neljas == puolipiste);

        assertTrue(oikeaMuoto, testiEiOnnistunut);
    }

    @ParameterizedTest(name="Ohjelma heittää virheen, mutta lopettaa koodilla 0.")
    @ValueSource(strings= { "" })
    void testaa_virheEiKaadaOhjelmaa(String aika) {
        try{
            TimeUtils.secToTime(Integer.parseInt(aika));
            fail("Ei tullut virhettä");
        } catch (NumberFormatException e){
            assertEquals("For input string: \"\"", e.getMessage());
        }
    }
}