package general.message;

public enum Chattype{
  PRIVATE(2),
  GROUP(16),
  HUB(2048);

  private int maxUsers;

  private Chattype(int maxUsers){
    this.maxUsers = maxUsers;
  }
  public int getMaxUsers(){
    return maxUsers;
  }
}
