
#include <android/log.h>


#include "trs_iodefs.h"
#include "trs.h"
#include "z80.h"
#include "trs_disk.h"
#include "trs_uart.h"
#include "trs_imp_exp.h"


#define DEBUG_TAG "TRS80"

#define NOT_IMPLEMENTED() \
    __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NOT_IMPLEMENTED: %s:%d", __FILE__, __LINE__); \
    exit(-1);

extern char trs_char_data[][MAXCHARS][TRS_CHAR_HEIGHT];

int trs_parse_command_line(int argc, char **argv, int *debug)
{
	NOT_IMPLEMENTED();
#if 0
      trs_emtsafe = True;
      *debug = False;
      trs_autodelay = True;
      trs_model = 3;
    trs_disk_dir = strdup(value.addr);
      grafyx_set_microlabs(False);
      trs_disk_doubler = TRSDISK_BOTH;
      trs_disk_truedam = False;
    cassette_default_sample_rate = strtol(value.addr, NULL, 0);
      title = strdup(value.addr);
      trs_uart_name = strdup(value.addr);
      trs_uart_switches = strtol(value.addr, NULL, 0);
      trs_kb_bracket(False);
#endif
}

int trs_screen_batched = 0;

void trs_screen_batch()
{
	NOT_IMPLEMENTED();
#if BATCH
  /* Defer screen updates until trs_screen_unbatch, then redraw screen
     if anything changed.  Unfortunately, this seems to slow things
     down, so it's disabled.  Probably what we should really be doing
     is rendering into an offscreen buffer when trs_screen_batched is
     set, then copying to the real screen in trs_screen_unbatch.  Also
     (and orthogonally) we should probably be keeping track of what
     part of the screen changed and only redrawing that part. */
  trs_screen_batched = 1;
#endif
}

void trs_screen_unbatch()
{
	NOT_IMPLEMENTED();
#if BATCH
  if (trs_screen_batched > 1) {
    trs_screen_batched = 0;
    trs_screen_refresh();
  } else {
    trs_screen_batched = 0;
  }
#endif
}

/*
 * show help
 */

void trs_show_help()
{
	NOT_IMPLEMENTED();
}

/* exits if something really bad happens */
void trs_screen_init()
{
	NOT_IMPLEMENTED();
}


/*
 * Flush output to X server
 */
inline void trs_x_flush()
{
	NOT_IMPLEMENTED();
}

/* 
 * Get and process X event(s).
 *   If wait is true, process one event, blocking until one is available.
 *   If wait is false, process as many events as are available, returning
 *     when none are left.
 * Handle interrupt-driven uart input here too.
 */ 
void trs_get_event(int wait)
{
	NOT_IMPLEMENTED();
}

void trs_screen_expanded(int flag)
{
	NOT_IMPLEMENTED();
}

void trs_screen_inverse(int flag)
{
	NOT_IMPLEMENTED();
}

void trs_screen_alternate(int flag)
{
	NOT_IMPLEMENTED();
}

void trs_screen_80x24(int flag)
{
	NOT_IMPLEMENTED();
}

void screen_init()
{
	NOT_IMPLEMENTED();
}

void
boxes_init(int foreground, int background, int width, int height, int expanded)
{
	NOT_IMPLEMENTED();
}


void trs_screen_refresh()
{
	NOT_IMPLEMENTED();
}

void trs_screen_write_char(int position, int char_index)
{
	NOT_IMPLEMENTED();
}

 /* Copy lines 1 through col_chars-1 to lines 0 through col_chars-2.
    Doesn't need to clear line col_chars-1. */
void trs_screen_scroll()
{
	NOT_IMPLEMENTED();
}

void grafyx_write_byte(int x, int y, char byte)
{
}

void grafyx_write_x(int value)
{
	NOT_IMPLEMENTED();
}

void grafyx_write_y(int value)
{
	NOT_IMPLEMENTED();
}

void grafyx_write_data(int value)
{
	NOT_IMPLEMENTED();
}

int grafyx_read_data()
{
	NOT_IMPLEMENTED();
	return 0;
}

void grafyx_write_mode(int value)
{
	NOT_IMPLEMENTED();
}

void grafyx_write_xoffset(int value)
{
	NOT_IMPLEMENTED();
}

void grafyx_write_yoffset(int value)
{
	NOT_IMPLEMENTED();
}

void grafyx_write_overlay(int value)
{
	NOT_IMPLEMENTED();
}

int grafyx_get_microlabs()
{
	NOT_IMPLEMENTED();
  return 0;
}

void grafyx_set_microlabs(int on_off)
{
	NOT_IMPLEMENTED();
}

/* Model III MicroLabs support */
void grafyx_m3_reset()
{
	NOT_IMPLEMENTED();
}

void grafyx_m3_write_mode(int value)
{
	NOT_IMPLEMENTED();
}

int grafyx_m3_write_byte(int position, int byte)
{
	NOT_IMPLEMENTED();
    return 0;
}

unsigned char grafyx_m3_read_byte(int position)
{
	NOT_IMPLEMENTED();
	return 0;
}

int grafyx_m3_active()
{
	NOT_IMPLEMENTED();
  return 0;
}

/* Switch HRG on (1) or off (0). */
void
hrg_onoff(int enable)
{
	NOT_IMPLEMENTED();
}

/* Write address to latch. */
void
hrg_write_addr(int addr, int mask)
{
	NOT_IMPLEMENTED();
}

/* Write byte to HRG memory. */
void
hrg_write_data(int data)
{
	NOT_IMPLEMENTED();
}

/* Read byte from HRG memory. */
int
hrg_read_data()
{
	NOT_IMPLEMENTED();
  return 0;
}

void trs_get_mouse_pos(int *x, int *y, unsigned int *buttons)
{
	NOT_IMPLEMENTED();
}

void trs_set_mouse_pos(int x, int y)
{
	NOT_IMPLEMENTED();
}

void trs_get_mouse_max(int *x, int *y, unsigned int *sens)
{
	NOT_IMPLEMENTED();
}

void trs_set_mouse_max(int x, int y, unsigned int sens)
{
	NOT_IMPLEMENTED();
}

int trs_get_mouse_type()
{
	NOT_IMPLEMENTED();
  /* !!Note: assuming 3-button mouse */
  return 1;
}
