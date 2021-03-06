/**************************************************************************
* Copyright (c) 2005, 2009 by Chris Gray, /k/ Embedded Java Solutions.    *
* All rights reserved.                                                    *
*                                                                         *
* Redistribution and use in source and binary forms, with or without      *
* modification, are permitted provided that the following conditions      *
* are met:                                                                *
* 1. Redistributions of source code must retain the above copyright       *
*    notice, this list of conditions and the following disclaimer.        *
* 2. Redistributions in binary form must reproduce the above copyright    *
*    notice, this list of conditions and the following disclaimer in the  *
*    documentation and/or other materials provided with the distribution. *
* 3. Neither the name of /k/ Embedded Java Solutions nor the names of     *
*    other contributors may be used to endorse or promote products        *
*    derived from this software without specific prior written permission.*
*                                                                         *
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED          *
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF    *
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.    *
* IN NO EVENT SHALL /K/ EMBEDDED JAVA SOLUTIONS OR OTHER CONTRIBUTORS BE  *
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR     *
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF    *
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR         *
* BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,   *
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE    *
* OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN  *
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                           *
**************************************************************************/

#ifndef _WMATH_H
#define _WMATH_H

#include <sys/types.h>

/*
** Integer representations. The most convenient type to hold an integer
** of at least as many bits as specified. We make all these types into
** words or double words, so that whatever we do, we are word aligned
** on 32-bit architectures.
*/

// TODO: always use the C99 types, the other stuff is unreliable.
typedef int                 wfp_flag;
typedef int                 wfp_int8;
typedef int                 wfp_int16;
typedef int                 wfp_int32;
#ifdef __BIT_TYPES_DEFINED__
typedef int64_t             wfp_int64;
#else
typedef long long           wfp_int64;
#endif

typedef unsigned int        wfp_uint8;
typedef unsigned int        wfp_uint32;
#ifdef __BIT_TYPES_DEFINED__
typedef u_int64_t           wfp_uint64;
#else
typedef unsigned long long  wfp_uint64;
#endif

/*
** Exact bit count containers for unsigned and signed
** quantities.
*/
#ifdef __BIT_TYPES_DEFINED__
typedef u_int32_t           wfp_bits32;
typedef u_int64_t           wfp_bits64;

typedef int32_t             wfp_sbits32;
typedef int64_t             wfp_sbits64;
#else
typedef unsigned int        wfp_bits32;
typedef unsigned long long  wfp_bits64;

typedef int                 wfp_sbits32;
typedef long long           wfp_sbits64;
#endif

/*
** Some predefined float and double constants.
*/
#define F_NAN			       (0x7fc00000)

#define F_ZERO                         (0x00000000)
#define F_MINUS_ZERO                   (0x80000000)
#define F_ONE                          (0x3f800000)
#define F_TWO                          (0x40000000)
#define F_ZERO_POINT_SEVEN_FIVE        (0x3f400000)
#define F_ZERO_POINT_FIVE              (0x3f000000)
#define F_FLOAT_MAX_VALUE              (0x7f7fffff)
#define F_FLOAT_MIN_VALUE              (0x00000001)

#define D_POSITIVE_INFINITY	       (0x7ff0000000000000LL)
#define D_NEGATIVE_INFINITY	       (0xfff0000000000000LL)
#define D_NAN			       (0x7ff8000000000000LL)
#define D_NANJIKES		       (0xfff8000000000000LL)

#define D_ZERO                         (0x0000000000000000LL)
#define D_ONE                          (0x3ff0000000000000LL)
#define D_TWO                          (0x4000000000000000LL)
#define D_THREE                        (0x4008000000000000LL)
#define D_FOUR                         (0x4010000000000000LL)
#define D_FIVE                         (0x4014000000000000LL)
#define D_SIX                          (0x4018000000000000LL)
#define D_SEVEN                        (0x401c000000000000LL)
#define D_EIGHT                        (0x4020000000000000LL)
#define D_NINE                         (0x4022000000000000LL)
#define D_TEN                          (0x4024000000000000LL)
#define D_ELEVEN                       (0x4026000000000000LL)
#define D_TWELVE                       (0x4028000000000000LL)


#define D_MINUS_ZERO                   (0x8000000000000000LL)
#define D_MINUS_ONE		       (0xbff0000000000000LL)
#define D_MINUS_FOUR                   (0xc010000000000000LL)

#define D_ZERO_POINT_FIVE              (0x3fe0000000000000LL)
#define D_ZERO_POINT_TWO_FIVE          (0x3fd0000000000000LL)
#define D_ZERO_POINT_ONE               (0x000000009999999aLL)
#define D_MINUS_ZERO_POINT_FIVE        (0xbfe0000000000000LL)

#define D_ONE_OVER_3                   (0x3fd5555555555555LL)
#define D_ONE_OVER_5                   (0x3fc999999999999aLL)
#define D_ONE_OVER_7                   (0x3fc2492492492492LL)
#define D_ONE_OVER_9                   (0x3fbc71c71c71c71cLL)
#define D_ONE_OVER_11                  (0x3fb745d1745d1746LL)
#define D_ONE_OVER_13                  (0x3fb3b13b13b13b14LL)
#define D_ONE_OVER_15                  (0x3fb1111111111111LL)
#define D_ONE_OVER_17                  (0x3fae1e1e1e1e1e1eLL)
#define D_ONE_OVER_19                  (0x3faaf286bca1af28LL)
#define D_ONE_OVER_21                  (0x3fa8618618618618LL)

#define D_LN_2                         (0x3fe62e42fefa39efLL)

#define D_PI                           (0x400921fb54442d18LL)
#define D_TWO_PI                       (0x401921fb54442d18LL)
#define D_HALF_PI                      (0x3ff921fb54442d18LL)
#define D_QUARTER_PI                   (0x3fe921fb54442d18LL)
#define D_MINUS_HALF_PI                (0xbff921fb54442d18LL)
#define D_MINUS_PI                     (0xc00921fb54442d18LL)

#define D_ONE_TO_THE_POWER_EIGHT       (0x4197d78400000000LL)
#define D_LONG_MIN_VALUE               (0xc3e0000000000000LL)
#define D_LONG_MAX_VALUE               (0x43e0000000000000LL)
#define D_DOUBLE_MAX_VALUE             (0x7fefffffffffffffLL)
#define D_DOUBLE_MIN_VALUE             (0x0000000000000001LL)
#define D_TINY                         (0x1ff0000000000000LL)
#define D_MINUS_TINY                   (0x9ff0000000000000LL)
#define D_LOG_BIG_NUM                  (0x40862e42fefa39efLL)

#define D_ZERO_MSW                     (0x00000000)
#define D_ZERO_LSW                     (0x00000000)

#define D_ONE_MSW                      (0x3ff00000)
#define D_ONE_LSW                      (0x00000000)

#define D_TWO_MSW                      (0x40000000)
#define D_TWO_LSW                      (0x00000000)

/*
** Some utility macros
*/
#define wfp_float32_negate(x)              ((x) ^ 0x80000000)
#define wfp_float32_is_negative(x)         ((x) & 0x80000000)

#define wfp_float64_negate(x)              ((x) ^ 0x8000000000000000LL)
#define wfp_float64_is_negative(x)         ((x) & 0x8000000000000000LL)

/*
-------------------------------------------------------------------------------
Software IEC/IEEE floating-point types.
-------------------------------------------------------------------------------
*/

typedef wfp_bits32 wfp_float32;
typedef wfp_bits64 wfp_float64;

/*
-------------------------------------------------------------------------------
Software IEC/IEEE floating-point underflow tininess-detection mode.
-------------------------------------------------------------------------------
*/

extern wfp_int8 wfp_float_detect_tininess;

enum {
    wfp_float_tininess_after_rounding  = 0,
    wfp_float_tininess_before_rounding = 1
};

/*
-------------------------------------------------------------------------------
Software IEC/IEEE floating-point rounding mode.
-------------------------------------------------------------------------------
*/

extern wfp_int8 wfp_float_rounding_mode;

enum {
    wfp_float_round_nearest_even = 0,
    wfp_float_round_to_zero      = 1,
    wfp_float_round_down         = 2,
    wfp_float_round_up           = 3
};

/*
-------------------------------------------------------------------------------
Software IEC/IEEE floating-point exception flags.
-------------------------------------------------------------------------------
*/

extern wfp_int8 float_exception_flags;

enum {
    wfp_float_flag_inexact   =  1,
    wfp_float_flag_underflow =  2,
    wfp_float_flag_overflow  =  4,
    wfp_float_flag_divbyzero =  8,
    wfp_float_flag_invalid   = 16
};

/*
-------------------------------------------------------------------------------
Routine to raise any or all of the software IEC/IEEE floating-point
exception flags.
-------------------------------------------------------------------------------
*/

void wfp_float_raise( wfp_int8 );

/*
-------------------------------------------------------------------------------
Software IEC/IEEE integer-to-floating-point conversion routines.
-------------------------------------------------------------------------------
*/

wfp_float32 wfp_int32_to_float32( wfp_int32 );
wfp_float64 wfp_int32_to_float64( wfp_int32 );

wfp_float32 wfp_int64_to_float32( wfp_int64 );
wfp_float64 wfp_int64_to_float64( wfp_int64 );

/*
-------------------------------------------------------------------------------
Software IEC/IEEE single-precision conversion routines.
-------------------------------------------------------------------------------
*/

wfp_int32 wfp_float32_to_int32( wfp_float32 );
wfp_int32 wfp_float32_to_int32_round_to_zero( wfp_float32 );
wfp_int64 wfp_float32_to_int64( wfp_float32 );
wfp_int64 wfp_float32_to_int64_round_to_zero( wfp_float32 );
wfp_float64 wfp_float32_to_float64( wfp_float32 );

/*
-------------------------------------------------------------------------------
Software IEC/IEEE single-precision operations.
-------------------------------------------------------------------------------
*/

wfp_flag wfp_float32_eq( wfp_float32, wfp_float32 );
wfp_flag wfp_float32_lt( wfp_float32, wfp_float32 );

wfp_float32 wfp_float32_add( wfp_float32, wfp_float32 );
wfp_float32 wfp_float32_sub( wfp_float32, wfp_float32 );
wfp_float32 wfp_float32_mul( wfp_float32, wfp_float32 );
wfp_float32 wfp_float32_div( wfp_float32, wfp_float32 );

/*
-------------------------------------------------------------------------------
Software IEC/IEEE double-precision conversion routines.
-------------------------------------------------------------------------------
*/

wfp_int32 wfp_float64_to_int32_round_to_zero( wfp_float64 );
wfp_int64 wfp_float64_to_int64_round_to_zero( wfp_float64 );
wfp_float32 wfp_float64_to_float32( wfp_float64 );

/*
-------------------------------------------------------------------------------
Software IEC/IEEE double-precision operations.
-------------------------------------------------------------------------------
*/

wfp_flag wfp_float64_eq( wfp_float64, wfp_float64 );
wfp_flag wfp_float64_lt( wfp_float64, wfp_float64 );

wfp_float64 wfp_float64_round_to_int( wfp_float64 );
wfp_float64 wfp_float64_add( wfp_float64, wfp_float64 );
wfp_float64 wfp_float64_sub( wfp_float64, wfp_float64 );
wfp_float64 wfp_float64_mul( wfp_float64, wfp_float64 );
wfp_float64 wfp_float64_div( wfp_float64, wfp_float64 );

#define WFP_LIT64(a)     a##LL

wfp_float32 wfp_float32_abs( wfp_float32 );
wfp_float64 wfp_float64_abs( wfp_float64 );
#ifdef NATIVE_MATH
wfp_float64 wfp_float64_sqrt( wfp_float64 );
wfp_float64 wfp_float64_sin( wfp_float64 );
wfp_float64 wfp_float64_cos( wfp_float64 );
wfp_float64 wfp_float64_tan( wfp_float64 );
wfp_float64 wfp_float64_asin( wfp_float64 );
wfp_float64 wfp_float64_atan( wfp_float64 );
wfp_float64 wfp_float64_log( wfp_float64 );
wfp_float64 wfp_float64_exp( wfp_float64 );
#endif

#define wfp_float64_signBit( float64_val ) ( (float64_val) & 0x8000000000000000LL )

wfp_flag wfp_float32_is_NaN(wfp_float32 a);
wfp_flag wfp_float32_is_Infinite(wfp_float32 a);
wfp_flag wfp_float64_is_NaN(wfp_float64 a);
wfp_flag wfp_float64_is_Infinite(wfp_float64 a);

typedef wfp_bits32 w_float;
typedef wfp_bits64 w_double;

#endif /* _WMATH_H */
