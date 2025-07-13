/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState } from "react";
import { ForgetPassword, ResetPassword } from "../service/UserService";
import { Mail, Key, Lock, AlertCircle, CheckCircle } from "lucide-react";
import { motion, AnimatePresence } from "framer-motion";
import { useNavigate } from "react-router-dom";

const ForgotPassword: React.FC = () => {
  const [email, setEmail] = useState("");
  const [kodeOtp, setKodeOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [isOtpStage, setIsOtpStage] = useState(false);
  const navigate = useNavigate();

  const handleEmailSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMsg("");
    setIsSuccess(false);
    setIsLoading(true);

    try {
      await ForgetPassword(email);
      setIsOtpStage(true);
      setErrorMsg("OTP sent successfully! Check your email.");
      setIsSuccess(true);
    } catch (error: any) {
      setErrorMsg(error.message || "Failed to send OTP. Please try again.");
      setIsSuccess(false);
    } finally {
      setIsLoading(false);
    }
  };

  const handleOtpSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMsg("");
    setIsSuccess(false);
    setIsLoading(true);

    try {
      const res = await ResetPassword(kodeOtp, newPassword);
      if (res.success) {
        setErrorMsg(res.message || "Password reset successfully!");
        setIsSuccess(true);
        navigate("/");
      } else {
        setErrorMsg(res.message || "Reset failed. Please check your OTP.");
        setIsSuccess(false);
      }
    } catch (error: any) {
      setErrorMsg(error.message || "Something went wrong. Please try again.");
      setIsSuccess(false);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-amber-100 via-orange-100 to-red-100 p-4">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="bg-white rounded-2xl shadow-xl w-full max-w-md p-8 relative overflow-hidden"
      >
        <div className="absolute inset-0 bg-[url('data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%2240%22 height=%2240%22 viewBox=%220 0 40 40%22%3E%3Cpath fill=%22%23f97316%22 fill-opacity=%220.1%22 d=%22M0 0h40v40H0z%22/%3E%3Ccircle cx=%2220%22 cy=%2220%22 r=%224%22 fill=%22%23f97316%22 fill-opacity=%220.3%22/%3E%3C/svg%3E')] opacity-50" />
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="text-center mb-8 relative z-10"
        >
          <h1 className="text-4xl font-bold text-amber-800">Martabak Lamaknyo</h1>
          <h2 className="text-xl font-semibold text-gray-700 mt-2">
            {isOtpStage ? "Reset Your Password" : "Forgot Password"}
          </h2>
        </motion.div>

        <AnimatePresence>
          {errorMsg && (
            <motion.div
              initial={{ opacity: 0, y: -20 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -20 }}
              className={`flex items-center gap-2 p-3 rounded-lg mb-6 relative z-10 ${
                isSuccess ? "bg-green-100 text-green-600" : "bg-red-100 text-red-600"
              }`}
            >
              {isSuccess ? <CheckCircle size={20} /> : <AlertCircle size={20} />}
              <p className="text-sm">{errorMsg}</p>
            </motion.div>
          )}
        </AnimatePresence>

        {!isOtpStage ? (
          <form onSubmit={handleEmailSubmit} className="space-y-6 relative z-10">
            <div className="relative">
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                Email
              </label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-amber-600" size={20} />
                <input
                  id="email"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="Enter your email"
                  className="w-full pl-10 pr-4 py-3 border border-amber-200 rounded-lg focus:ring-2 focus:ring-amber-500 focus:border-transparent transition-all duration-300 bg-amber-50/50"
                  required
                />
              </div>
            </div>

            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              type="submit"
              disabled={isLoading}
              className={`w-full py-3 px-4 rounded-lg font-medium text-lg shadow-md hover:shadow-lg transition-all duration-300 ${
                isLoading ? "bg-amber-400 text-white cursor-not-allowed" : "bg-amber-600 text-white hover:bg-amber-700"
              }`}
            >
              {isLoading ? (
                <span className="flex items-center justify-center gap-2">
                  <svg className="animate-spin h-5 w-5 text-white" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z" />
                  </svg>
                  Sending...
                </span>
              ) : (
                "Send OTP"
              )}
            </motion.button>
          </form>
        ) : (
          <form onSubmit={handleOtpSubmit} className="space-y-6 relative z-10">
            <div className="relative">
              <label htmlFor="kodeOtp" className="block text-sm font-medium text-gray-700 mb-1">
                OTP Code
              </label>
              <div className="relative">
                <Key className="absolute left-3 top-1/2 transform -translate-y-1/2 text-amber-600" size={20} />
                <input
                  id="kodeOtp"
                  type="text"
                  value={kodeOtp}
                  onChange={(e) => setKodeOtp(e.target.value)}
                  placeholder="Enter OTP code"
                  className="w-full pl-10 pr-4 py-3 border border-amber-200 rounded-lg focus:ring-2 focus:ring-amber-500 focus:border-transparent transition-all duration-300 bg-amber-50/50"
                  required
                />
              </div>
            </div>

            <div className="relative">
              <label htmlFor="newPassword" className="block text-sm font-medium text-gray-700 mb-1">
                New Password
              </label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-amber-600" size={20} />
                <input
                  id="newPassword"
                  type="password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  placeholder="Enter new password"
                  className="w-full pl-10 pr-4 py-3 border border-amber-200 rounded-lg focus:ring-2 focus:ring-amber-500 focus:border-transparent transition-all duration-300 bg-amber-50/50"
                  required
                />
              </div>
            </div>

            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              type="submit"
              disabled={isLoading}
              className={`w-full py-3 px-4 rounded-lg font-medium text-lg shadow-md hover:shadow-lg transition-all duration-300 ${
                isLoading ? "bg-amber-400 text-white cursor-not-allowed" : "bg-amber-600 text-white hover:bg-amber-700"
              }`}
            >
              {isLoading ? (
                <span className="flex items-center justify-center gap-2">
                  <svg className="animate-spin h-5 w-5 text-white" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z" />
                  </svg>
                  Resetting...
                </span>
              ) : (
                "Reset Password"
              )}
            </motion.button>
          </form>
        )}

        <div className="mt-6 text-center relative z-10">
          <p className="text-sm text-gray-600">
            Back to{" "}
            <a href="/login" className="text-amber-600 hover:text-amber-800 font-medium">
              Login
            </a>
          </p>
        </div>
      </motion.div>
    </div>
  );
};

export default ForgotPassword;