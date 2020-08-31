package server.message;

public enum Chattype{
  PRIVATE(2),
  GROUP(16),
  HUB(2048);

  protected int maxUsers;

  private Chattype(int maxUsers){
    this.maxUsers = maxUsers;
  }
  public int getMaxUsers(){
    return maxUsers;
  }
}
