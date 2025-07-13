import type { GenericResponseType } from "../types";

// src/services/loginService.ts
export const LoginService = async (email: string, password: string) => {
    const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
        throw new Error("Login gagal. Periksa email atau password.");
    }

    return response.json();
};

export const ForgetPassword = async (email: string) => {
    const response = await fetch("http://localhost:8080/api/auth/forgot-password", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email }),
    });

    if (!response.ok) {
        throw new Error("Reset password gagal. Periksa email.");
    }
}

export const ResetPassword = async (
    kodeOtp: string,
    newPassword: string
  ): Promise<GenericResponseType<null>> => {
    const response = await fetch("http://localhost:8080/api/auth/reset-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        kodeOtp,
        newPassword,
      }),
    });
  
    const result: GenericResponseType<null> = await response.json();
  
    if (!response.ok || !result.success) {
      throw new Error(result.message || "Failed to reset password");
    }
  
    return result;
  };
