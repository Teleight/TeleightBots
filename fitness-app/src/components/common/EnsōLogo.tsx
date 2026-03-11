import React from 'react';
import { View, StyleSheet } from 'react-native';
import Svg, { Path, Defs, LinearGradient, Stop } from 'react-native-svg';
import { colors } from '../../config/theme';

interface EnsoLogoProps {
  size?: number;
}

export const EnsoLogo: React.FC<EnsoLogoProps> = ({ size = 160 }) => {
  const scale = size / 160;

  return (
    <View style={[styles.container, { width: size, height: size }]}>
      <Svg width={size} height={size} viewBox="0 0 160 160">
        <Defs>
          <LinearGradient id="ensoGrad" x1="0%" y1="0%" x2="100%" y2="100%">
            <Stop offset="0%" stopColor="#E60000" />
            <Stop offset="50%" stopColor="#CC0000" />
            <Stop offset="100%" stopColor="#990000" />
          </LinearGradient>
          <LinearGradient id="waveGrad" x1="0%" y1="0%" x2="100%" y2="0%">
            <Stop offset="0%" stopColor="#F5F0E8" stopOpacity="0.1" />
            <Stop offset="30%" stopColor="#F5F0E8" stopOpacity="1" />
            <Stop offset="70%" stopColor="#F5F0E8" stopOpacity="1" />
            <Stop offset="100%" stopColor="#F5F0E8" stopOpacity="0.2" />
          </LinearGradient>
        </Defs>

        {/* Cerchio Ensō - tratto pennello con apertura in alto a destra */}
        <Path
          d={`
            M 95 22
            C 120 28, 142 52, 144 80
            C 146 108, 130 136, 100 144
            C 70 152, 36 140, 22 114
            C 8 88, 14 54, 36 34
            C 52 20, 74 18, 88 22
          `}
          stroke="url(#ensoGrad)"
          strokeWidth={12 * scale}
          strokeLinecap="round"
          fill="none"
          opacity={0.95}
        />

        {/* Texture pennello - tratti sovrapposti per effetto brush */}
        <Path
          d={`
            M 93 24
            C 118 30, 138 50, 141 78
            C 144 106, 128 134, 100 141
          `}
          stroke="#CC0000"
          strokeWidth={3 * scale}
          strokeLinecap="round"
          fill="none"
          opacity={0.4}
        />
        <Path
          d={`
            C 70 148, 38 138, 24 112
            C 10 86, 16 56, 38 36
            C 54 22, 76 20, 90 24
          `}
          stroke="#E60000"
          strokeWidth={2 * scale}
          strokeLinecap="round"
          fill="none"
          opacity={0.3}
        />

        {/* Onda centrale - S-curve elegante */}
        <Path
          d={`
            M 42 82
            C 55 72, 68 72, 80 80
            C 92 88, 105 88, 118 78
          `}
          stroke="url(#waveGrad)"
          strokeWidth={3.5 * scale}
          strokeLinecap="round"
          fill="none"
        />
        {/* Punta finale dell'onda - tratto sottile che sfuma */}
        <Path
          d={`
            M 116 79
            C 120 76, 124 74, 126 74
          `}
          stroke="#F5F0E8"
          strokeWidth={1.5 * scale}
          strokeLinecap="round"
          fill="none"
          opacity={0.5}
        />
      </Svg>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
  },
});
