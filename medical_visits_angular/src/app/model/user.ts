export class User {
  role: string;
  token: string;

  constructor(role: string, token: string) {
    this.role = role;
    this.token = token;
  }
}
