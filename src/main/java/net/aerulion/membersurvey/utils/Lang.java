package net.aerulion.membersurvey.utils;

public class Lang {

  public static final String CHAT_PREFIX = "§7[§e§lUmfrage§7] ";

  public static final String INVENTORY_NAME_SURVEY = "§2§l▶ Umfrage";

  public static final String NBT_KEY_SURVEY_OPTION = "MemberSurveyOption";
  public static final String NBT_KEY_SURVEY_ID = "MemberSurveyID";

  public static final String MESSAGE_SURVEY_ADDED =
      CHAT_PREFIX + "§aDie Umfrage wurde erfolgreich hinzugefügt.";
  public static final String MESSAGE_SURVEY_AVAILABLE_1 = "§eDu kannst an einer Umfrage teilnehmen.";
  public static final String MESSAGE_SURVEY_AVAILABLE_2 = "§eGib §o/umfrage§e ein und erhalte am Ende eine Belohnung.";
  public static final String MESSAGE_NO_SURVEY_AVAILABLE =
      CHAT_PREFIX + "§7Du hast bereits an allen verfügbaren Umfragen teilgenommen.";
  public static final String MESSAGE_SURVEY_COMPLETED_1 = CHAT_PREFIX
      + "§aWir bedanken uns für deine Teilnahme an der Umfrage! Die Belohnung in Höhe von §e§l";
  public static final String MESSAGE_SURVEY_COMPLETED_2 = " CT§a wurde auf dein Konto gutgeschrieben.";

  public static final String ERROR_NO_PERMISSION =
      CHAT_PREFIX + "§cFehler: Du hast nicht die benötigten Rechte.";
  public static final String ERROR_WRONG_ARGUMENTS = CHAT_PREFIX + "§cFehler: Falsche Argumente.";
  public static final String ERROR_NO_PLAYER =
      CHAT_PREFIX + "§cFehler: Dieser Befehl kann nur als Spieler ausgeführt werden.";
  public static final String ERROR_TRANSACTION_FAILED = CHAT_PREFIX
      + "§cTransaktion fehlgeschlagen! Bitte wende dich an einen Staffler um deine Belohnung dennoch zu erhalten.";
  public static final String ERROR_SURVEY_ID_NOT_FOUND =
      CHAT_PREFIX + "§cFehler: Es wurde keine Umfrage mit dieser ID gefunden.";

  public static final String CONSOLE_TRANSACTION_FAILED =
      CHAT_PREFIX + "§cTransaktion fehlgeschlagen: ";
  public static final String CONSOLE_ENABLING = CHAT_PREFIX + "§aAktiviere Plugin...";
  public static final String CONSOLE_PLUGIN_ENABLED = CHAT_PREFIX + "§aDas Plugin wurde aktiviert.";
  public static final String CONSOLE_DISABLING = CHAT_PREFIX + "§aDeaktiviere Plugin...";
  public static final String CONSOLE_PLUGIN_DISABLED =
      CHAT_PREFIX + "§aDas Plugin wurde deaktiviert.";
  public static final String CONSOLE_SURVEYS_LOADED = "§a Umfragen wurden geladen. Dauerte §e";

  public static final String CONVERSATION_QUESTION = "Schreibe die Frage in den Chat. Schreibe 'stop' um den Vorgang abzubrechen.";
  public static final String CONVERSATION_OPTIONS = "Schreibe die Antwortmöglichkeiten in den Chat. Trenne die einzelnen Antworten durch ein '#'. Verwende kein Leerzeichen vor oder nach dem Trennsymbol. Schreibe 'stop' um den Vorgang abzubrechen.";
  public static final String CONVERSATION_REWARD = "Schreibe die Belohnung in CT in den Chat. Schreibe 'stop' um den Vorgang abzubrechen.";
}