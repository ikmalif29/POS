/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState } from "react";
import Cookies from "js-cookie";
import { Lock, Mail, AlertCircle, Key } from "lucide-react";
import { motion, AnimatePresence } from "framer-motion";
import { LoginService } from "../service/UserService";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const res = await LoginService(email, password);
      console.log("Login berhasil:", res);
      Cookies.set("token", res.token);
    } catch (error: any) {
      setErrorMsg(error.message);
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
          <h2 className="text-xl font-semibold text-gray-700 mt-2">Cashier System Login</h2>
        </motion.div>

        <AnimatePresence>
          {errorMsg && (
            <motion.div
              initial={{ opacity: 0, y: -20 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -20 }}
              className="flex items-center gap-2 bg-red-100 text-red-600 p-3 rounded-lg mb-6 relative z-10"
            >
              <AlertCircle size={20} />
              <p className="text-sm">{errorMsg}</p>
            </motion.div>
          )}
        </AnimatePresence>

        <form onSubmit={handleSubmit} className="space-y-6 relative z-10">
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

          <div className="relative">
            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
              Password
            </label>
            <div className="relative">
              <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-amber-600" size={20} />
              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                className="w-full pl-10 pr-4 py-3 border border-amber-200 rounded-lg focus:ring-2 focus:ring-amber-500 focus:border-transparent transition-all duration-300 bg-amber-50/50"
                required
              />
            </div>
          </div>

          <div className="flex justify-between gap-4">
            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              type="submit"
              className="flex-1 bg-amber-600 text-white py-3 px-4 rounded-lg hover:bg-amber-700 transition-all duration-300 font-medium text-lg shadow-md hover:shadow-lg"
            >
              Sign In
            </motion.button>
            <motion.a
              href="/forgot-password"
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              className="flex-1 flex items-center justify-center gap-2 bg-gray-200 text-gray-700 py-3 px-4 rounded-lg hover:bg-gray-300 transition-all duration-300 font-medium text-sm"
            >
              <Key size={16} />
              Forgot Password
            </motion.a>
          </div>
        </form>
      </motion.div>
    </div>
  );
};

export default Login;